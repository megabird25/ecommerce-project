package com.weihan.ecommerce_backend.controller;

import com.weihan.ecommerce_backend.model.dto.Result;
import com.weihan.ecommerce_backend.model.entity.User;
import com.weihan.ecommerce_backend.model.entity.UserAddress;
import com.weihan.ecommerce_backend.service.UserAddressService;
import com.weihan.ecommerce_backend.service.UserService;
import com.weihan.ecommerce_backend.utils.ThreadLocalUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user/address")
public class UserAddressController {

    private final UserService userService;
    private final UserAddressService userAddressService;

    public UserAddressController(UserService userService, UserAddressService userAddressService) {
        this.userService = userService;
        this.userAddressService = userAddressService;
    }

    @GetMapping
    public Result<List<UserAddress>> getUserAddress() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");

        List<UserAddress> userAddresses = userAddressService.findAllByUserId(userId);

        return Result.success("獲取成功", userAddresses);
    }

    @PostMapping
    public Result<UserAddress> createUserAddress(@RequestBody UserAddress userAddress) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        User user = userService.findById(userId).get();
        userAddress.setUser(user);
        userAddress.setIsDefault((byte) 0);

        UserAddress userAddressCreated = userAddressService.save(userAddress);

        return Result.success("建立成功", userAddressCreated);
    }

    @PutMapping("/{id}")
    public Result<UserAddress> updateUserAddress(@PathVariable Integer id, @RequestBody UserAddress userAddressUpdatedData) {
        Optional<UserAddress> userAddressOpt = userAddressService.findById(id);

        if (userAddressOpt.isPresent()) {
            UserAddress userAddress = userAddressOpt.get();
            Map<String, Object> claims = ThreadLocalUtil.get();
            Integer userId = (Integer) claims.get("id");

            if (userAddress.getUser().getId().equals(userId)) {
                userAddress.setReceiver(userAddressUpdatedData.getReceiver());
                userAddress.setContact(userAddressUpdatedData.getContact());
                userAddress.setAddress(userAddressUpdatedData.getAddress());

                UserAddress updatedUserAddress = userAddressService.save(userAddress);

                return Result.success("修改成功", updatedUserAddress);
            }
        }

        return Result.error("操作有誤");
    }

    @PatchMapping("/{id}")
    public Result<String> updateUserAddressDefault(@PathVariable Integer id) {
        Optional<UserAddress> userAddressOpt = userAddressService.findById(id);

        if (userAddressOpt.isPresent()) {
            UserAddress userAddress = userAddressOpt.get();
            Map<String, Object> claims = ThreadLocalUtil.get();
            Integer userId = (Integer) claims.get("id");

            if (userAddress.getUser().getId().equals(userId)) {
                // 如有已預設默認地址則更改為默認
                Optional<UserAddress> defaultUserAddress = userAddressService.findDefaultByUserId(userId);
                if (defaultUserAddress.isPresent()) {
                    UserAddress oldDefault = defaultUserAddress.get();
                    oldDefault.setIsDefault((byte) 0);
                    userAddressService.save(oldDefault);
                }

                userAddress.setIsDefault((byte) 1);
                userAddressService.save(userAddress);

                return Result.success("修改成功");
            }
        }

        return Result.error("操作有誤");
    }

    @DeleteMapping("/{id}")
    public Result<Map<String, String>> deleteUserAddress(@PathVariable Integer id) {
        Optional<UserAddress> userAddressOpt = userAddressService.findById(id);

        if (userAddressOpt.isPresent()) {
            UserAddress userAddress = userAddressOpt.get();
            Map<String, Object> claims = ThreadLocalUtil.get();
            Integer userId = (Integer) claims.get("id");

            if (userAddress.getUser().getId().equals(userId)) {
                userAddressService.delete(userAddress);
                Map<String, String> result = new HashMap<>();
                result.put("id", id.toString());

                return Result.success("刪除成功", result);
            }
        }

        return Result.error("操作有誤");
    }
}
