package com.weihan.ecommerce_backend.service.impl;

import com.weihan.ecommerce_backend.model.entity.Category;
import com.weihan.ecommerce_backend.repository.CategoryRepository;
import com.weihan.ecommerce_backend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getParentCategories() {
        return categoryRepository.findByParentCategoryIsNull();
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

}
