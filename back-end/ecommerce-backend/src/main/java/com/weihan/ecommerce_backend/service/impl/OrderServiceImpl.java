package com.weihan.ecommerce_backend.service.impl;

import com.weihan.ecommerce_backend.model.dto.PageResponse;
import com.weihan.ecommerce_backend.model.entity.Order;
import com.weihan.ecommerce_backend.repository.OrderRepository;
import com.weihan.ecommerce_backend.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public PageResponse<Order> findByUserId(Integer userId, Pageable pageable, String status) {
        Page<Order> orders = null;
        if (status.equals("0")) {
            orders = orderRepository.findByUserId(userId, pageable);
        } else {
            orders = orderRepository.findByUserIdAndStatus(userId, pageable, status);
        }

        return createPageResponseOfOrder(orders);
    }

    private PageResponse<Order> createPageResponseOfOrder(Page<Order> orders) {
        PageResponse<Order> pageResponse = new PageResponse<>();

        pageResponse.setPage(orders.getNumber()+1);
        pageResponse.setPageSize(orders.getSize());
        pageResponse.setTotal(orders.getTotalElements());
        pageResponse.setTotalPages(orders.getTotalPages());
        pageResponse.setData(orders.getContent());

        return pageResponse;
    }

}
