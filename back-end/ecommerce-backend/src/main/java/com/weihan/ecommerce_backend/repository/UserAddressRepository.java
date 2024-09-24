package com.weihan.ecommerce_backend.repository;

import com.weihan.ecommerce_backend.model.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

    List<UserAddress> findByUserId(Integer id);

    @Query("SELECT ua FROM UserAddress ua WHERE ua.user.id = :userId AND ua.isDefault = 1")
    Optional<UserAddress> findDefaultByUserId(Integer userId);
}
