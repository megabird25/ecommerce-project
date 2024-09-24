package com.weihan.ecommerce_backend.service.impl;

import com.weihan.ecommerce_backend.model.entity.UserAddress;
import com.weihan.ecommerce_backend.repository.UserAddressRepository;
import com.weihan.ecommerce_backend.service.UserAddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressRepository userAddressRepository;

    public UserAddressServiceImpl(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public List<UserAddress> findAllByUserId(Integer userId) {
        return userAddressRepository.findByUserId(userId);
    }

    @Override
    public Optional<UserAddress> findById(Integer id) {
        return userAddressRepository.findById(id);
    }

    @Override
    public UserAddress save(UserAddress userAddress) {
        return userAddressRepository.save(userAddress);
    }

    @Override
    public Optional<UserAddress> findDefaultByUserId(Integer userId) {
        return userAddressRepository.findDefaultByUserId(userId);
    }

    @Override
    public void delete(UserAddress userAddress) {
        userAddressRepository.delete(userAddress);
    }
}
