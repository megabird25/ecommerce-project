package com.weihan.ecommerce_backend.controller;

import com.weihan.ecommerce_backend.model.dto.Account;
import com.weihan.ecommerce_backend.model.dto.Result;
import com.weihan.ecommerce_backend.model.entity.User;
import com.weihan.ecommerce_backend.service.S3Service;
import com.weihan.ecommerce_backend.service.UserService;
import com.weihan.ecommerce_backend.utils.JwtUtil;
import com.weihan.ecommerce_backend.utils.ThreadLocalUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.io.File;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate;
    private final S3Service s3Service;

    public UserController(UserService userService, StringRedisTemplate stringRedisTemplate, S3Service s3Service) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.s3Service = s3Service;
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody Account account) {
        Optional<User> userOpt = userService.findByUsername(account.getUsername());

        if (userOpt.isEmpty()) {
            User newUser = new User();
            String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
            newUser.setUsername(account.getUsername());
            newUser.setPassword(hashedPassword);
            userService.save(newUser);

            return Result.success("註冊成功", newUser);
        }

        return Result.error("此用戶名稱已被使用");
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody Account account, HttpServletResponse response) {
        Optional<User> userOpt = userService.findByUsername(account.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (BCrypt.checkpw(account.getPassword(), user.getPassword())) {
                // 生成 token
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", user.getId());
                claims.put("username", user.getUsername());
                String token = JwtUtil.genToken(claims);

                // 把 token 儲存到 redis 中
                ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
                operations.set(user.getId().toString(), token, 24, TimeUnit.HOURS);

                // 生成並配置 cookie
                Cookie cookie = new Cookie("jwt_token", token);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(24 * 60 * 60);
                response.addCookie(cookie);

                return Result.success("登入成功", user);
            }
        }

        return Result.error("帳號或密碼不正確");
    }

    @PostMapping("/logout")
    public Result<User> logout(HttpServletResponse response) {
        String id = ThreadLocalUtil.get().get("id").toString();
        stringRedisTemplate.delete(id);

        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return Result.success("已登出帳號");
    }

    @PutMapping("/update")
    public Result<User> updateUserInfo(@RequestBody User userUpdatedData) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        Optional<User> userOpt = userService.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEmail(userUpdatedData.getEmail());
            user.setMobile(userUpdatedData.getMobile());
            user.setNickname(userUpdatedData.getNickname());
            user.setGender(userUpdatedData.getGender());
            user.setBirthday(userUpdatedData.getBirthday());
            User updatedUser = userService.save(user);
            return Result.success("修改成功", updatedUser);
        }

        return Result.error("找不到該用戶");
    }

    @PatchMapping("/password")
    public Result<String> resetUserPassword(@RequestBody Map<String, String> userData) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        Optional<User> userOpt = userService.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String oldPassword = userData.get("old_password");

            if (BCrypt.checkpw(oldPassword, user.getPassword())) {
                String newPassword = userData.get("new_password");
                String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                user.setPassword(hashedPassword);
                userService.save(user);

                return Result.success("修改成功");
            }

            return Result.error("原密碼不正確");
        }

        return Result.error("找不到該用戶");
    }

    @PostMapping("/avatar")
    public Result<String> updateUserAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            Result.error("找不到檔案");
        }

        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        Optional<User> userOpt = userService.findByUsername(username);

        if (userOpt.isPresent()) {
            // 將 MultipartFile 轉換為 File
            File tempFile = File.createTempFile("temp", file.getOriginalFilename());
            file.transferTo(tempFile);

            // 指定檔案名稱為 用戶名 + 副檔名
            int idx = file.getOriginalFilename().lastIndexOf(".");
            if (idx == -1) {
                return Result.error("不正確的檔案類型");
            }
            String fileName = username + file.getOriginalFilename().substring(idx);

            // 上傳文件到 S3
            String fileUrl = s3Service.uploadUserAvatar(tempFile, fileName);

            // 刪除本地臨時文件
            tempFile.delete();

            User user = userOpt.get();
            user.setAvatar(fileUrl);
            userService.save(user);

            return Result.success("修改成功", fileUrl);
        }

        return Result.error("找不到該用戶");
    }
}
