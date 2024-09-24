package com.weihan.ecommerce_backend.service;

import com.weihan.ecommerce_backend.model.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getParentCategories();

    Optional<Category> findById(Integer id);

}
