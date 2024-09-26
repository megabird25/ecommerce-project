package com.weihan.ecommerce_backend.controller;

import com.weihan.ecommerce_backend.model.dto.CategoryGoods;
import com.weihan.ecommerce_backend.model.dto.PageResponse;
import com.weihan.ecommerce_backend.model.dto.Result;
import com.weihan.ecommerce_backend.model.entity.Category;
import com.weihan.ecommerce_backend.model.entity.Product;
import com.weihan.ecommerce_backend.service.CategoryService;
import com.weihan.ecommerce_backend.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public Result<List<Category>> getCategories() {
        List<Category> categories = categoryService.getParentCategories();

        return Result.success("獲取成功", categories);
    }

    @GetMapping("/{id}")
    public Result<Map<String, Object>> getCategoryPage(@PathVariable Integer id) {
        Optional<Category> categoryOpt = categoryService.findById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();

            if (category.getParentCategory() == null) {
                List<Category> subCategories = category.getSubCategories();
                List<CategoryGoods> categoryGoodsList = new ArrayList<>();
                for (Category subCategory : subCategories) {
                    List<Product> goods = productService.getProductsByCategoryId(subCategory.getId(), 4);
                    categoryGoodsList.add(new CategoryGoods(subCategory, goods));
                }

                Map<String, Object> result = new HashMap<>();
                result.put("id", category.getId());
                result.put("name", category.getName());
                result.put("children", categoryGoodsList);

                return Result.success("獲取成功", result);
            }
        }

        return Result.error("找不到該分類");
    }

    @GetMapping("/sub/{id}")
    public Result<PageResponse<Product>> getProductsBySubCategoryId(
            @PathVariable Integer id,
            @RequestParam Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "publishTime") String sortField
    ) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").descending());
        PageResponse<Product> pageResponse = productService.findByCategoryId(id, pageable, sortField);


        if (!pageResponse.getData().isEmpty()) {
            return Result.success("獲取成功", pageResponse);
        }

        return Result.error("找不到該子分類");
    }
}
