package com.weihan.ecommerce_backend.service;

import com.weihan.ecommerce_backend.model.dto.PageResponse;
import com.weihan.ecommerce_backend.model.entity.Order;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Order save(Order order);

    PageResponse<Order> findByUserId(Integer userId, Pageable pageable, String status);

}
