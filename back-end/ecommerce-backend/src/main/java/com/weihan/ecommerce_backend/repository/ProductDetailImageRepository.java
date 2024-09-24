package com.weihan.ecommerce_backend.repository;

import com.weihan.ecommerce_backend.model.entity.ProductDetailImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailImageRepository extends JpaRepository<ProductDetailImage, Integer> {

    List<ProductDetailImage> findByProductId(Integer id);

}
