package com.weihan.ecommerce_backend.controller;

import com.weihan.ecommerce_backend.model.dto.PageResponse;
import com.weihan.ecommerce_backend.model.dto.Result;
import com.weihan.ecommerce_backend.model.entity.Order;
import com.weihan.ecommerce_backend.model.entity.OrderDetail;
import com.weihan.ecommerce_backend.model.entity.Product;
import com.weihan.ecommerce_backend.model.entity.User;
import com.weihan.ecommerce_backend.service.OrderService;
import com.weihan.ecommerce_backend.service.ProductService;
import com.weihan.ecommerce_backend.service.UserService;
import com.weihan.ecommerce_backend.utils.ThreadLocalUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user/order")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderController(UserService userService, OrderService orderService, ProductService productService) {
        this.userService = userService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @PostMapping
    public Result<Order> createOrder(@RequestBody Order order) {
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            Optional<Product> productOpt = productService.findById(orderDetail.getId());
            if (productOpt.isPresent()) {
                orderDetail.setProduct(productOpt.get());
                orderDetail.setOrder(order);
                orderDetail.setId(null);
            } else {
                return Result.error("有找不到的商品");
            }
        }

        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");
        User user = userService.findById(userId).get();
        order.setUser(user);

        Order createdOrder = orderService.save(order);

        return Result.success("操作成功", createdOrder);
    }

    @GetMapping
    public Result<PageResponse<Order>> getOrder(
            @RequestParam Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "0") String status
    ) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = (Integer) claims.get("id");

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").descending());
        PageResponse<Order> orders = orderService.findByUserId(userId, pageable, status);

        List<Order> orderList = orders.getData();
        for (Order order : orderList) {
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.setOrder(null);
            }
        }

        return Result.success("獲取成功", orders);
    }
}
