package com.weihan.ecommerce_backend.model.dto;

import com.weihan.ecommerce_backend.model.entity.Brand;
import com.weihan.ecommerce_backend.model.entity.Category;
import com.weihan.ecommerce_backend.model.entity.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDetailResponse {

    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer inventory;
    private String imageUrl;
    private Integer salesCount;
    private Integer commentCount;
    private Integer collectCount;
    private LocalDateTime createdDate;
    private List<Category> category;
    private Brand brand;
    private List<String> details;
    private List<Product> similarProducts;

}
