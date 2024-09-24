package com.weihan.ecommerce_backend.service;

import com.weihan.ecommerce_backend.model.entity.UserAddress;

import java.util.List;
import java.util.Optional;

public interface UserAddressService {

    List<UserAddress> findAllByUserId(Integer userId);

    Optional<UserAddress> findById(Integer id);

    UserAddress save(UserAddress userAddress);

    Optional<UserAddress> findDefaultByUserId(Integer userId);

    void delete(UserAddress userAddress);
}
