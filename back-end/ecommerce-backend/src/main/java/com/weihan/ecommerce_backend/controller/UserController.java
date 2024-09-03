package com.weihan.ecommerce_backend.controller;

import com.weihan.ecommerce_backend.model.dto.Result;
import com.weihan.ecommerce_backend.model.entity.User;
import com.weihan.ecommerce_backend.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Result<User> register(@RequestBody User user) {
        Optional<User> userOpt = userService.findByAccount(user.getAccount());

        if (userOpt.isEmpty()) {
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
            User newUser = userService.save(user);
            return Result.success("註冊成功", newUser);
        }

        return Result.error("此用戶名稱已被使用");
    }

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Map<String, String> userData) {
        String username = userData.get("account");
        String password = userData.get("password");

        Optional<User> userOpt = userService.findByAccount(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (BCrypt.checkpw(password, user.getPassword())) {
                String token = "token";
                Map<String, String> map = new HashMap<>();
                map.put("id", user.getId().toString());
                map.put("account", user.getAccount());
                map.put("email", user.getEmail());
                map.put("mobile", user.getMobile());
                map.put("avatar", user.getAvatar());
                map.put("nickname", user.getNickname());
                map.put("gender", user.getGender());
                map.put("birthday", user.getBirthday().toString());
                map.put("token", token);
                return Result.success("登入成功", map);
            }
        }

        return Result.error("你的帳號或密碼不正確");
    }

    @PutMapping("/{id}")
    public Result<User> updateUserInfo(@PathVariable Integer id, @RequestBody User userDetails) {
        Optional<User> userOpt = userService.findById(id);


        if (userOpt.isPresent()){
            User user = userOpt.get();
            user.setEmail(userDetails.getEmail());
            user.setMobile(userDetails.getMobile());
            user.setAvatar(userDetails.getAvatar());
            user.setNickname(userDetails.getNickname());
            user.setGender(userDetails.getGender());
            user.setBirthday(userDetails.getBirthday());
            User updatedUser = userService.save(user);
            return Result.success("修改成功", updatedUser);
        }

        return Result.error("操作有誤");
    }

    @PatchMapping("/password/{id}")
    public Result<String> updateUserPassword(@PathVariable Integer id, @RequestBody Map<String, String> userData) {
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
}
