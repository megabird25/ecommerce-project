package com.weihan.ecommerce_backend.service;

import java.util.List;

public interface ProductDetailImageService {

    List<String> findByProductId(Integer id);
}
