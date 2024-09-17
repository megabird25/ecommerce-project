package com.weihan.ecommerce_backend.service;

import com.weihan.ecommerce_backend.model.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    User save(User user);

    Optional<User> findById(Integer id);

}
