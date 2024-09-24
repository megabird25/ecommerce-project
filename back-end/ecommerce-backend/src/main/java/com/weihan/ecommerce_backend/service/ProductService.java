package com.weihan.ecommerce_backend.service;

import com.weihan.ecommerce_backend.model.dto.PageResponse;
import com.weihan.ecommerce_backend.model.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    PageResponse<Product> findByCategoryId(Integer id, Pageable pageable, String sortField);

    List<Product> getProductsByCategoryId(Integer id, Integer num);

    Optional<Product> findDetailById(Integer id);

    PageResponse<Product> findByNameContaining(String keyword, Pageable pageable, String sortField);

    List<Product> getProducts(Integer num);

    List<Product> getProductsByCategoryId(List<Integer> ids, Integer num);
}
