package com.weihan.ecommerce_backend.repository;

import com.weihan.ecommerce_backend.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findByUserId(Integer userId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status = :status")
    Page<Order> findByUserIdAndStatus(Integer userId, Pageable pageable, String status);

}
