package com.weihan.ecommerce_backend.controller;

import com.weihan.ecommerce_backend.model.dto.Result;
import com.weihan.ecommerce_backend.model.dto.CategoryGoods;
import com.weihan.ecommerce_backend.model.entity.Category;
import com.weihan.ecommerce_backend.model.entity.Product;
import com.weihan.ecommerce_backend.service.CategoryService;
import com.weihan.ecommerce_backend.service.ProductService;
import com.weihan.ecommerce_backend.service.S3Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final S3Service s3Service;
    private final CategoryService categoryService;
    private final ProductService productService;

    public HomeController(S3Service s3Service, CategoryService categoryService, ProductService productService) {
        this.s3Service = s3Service;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/banner")
    public Result<List<String>> getBanner() {
        List<String> homeBannerUrl = s3Service.getHomeBannerUrl();

        return Result.success("獲取成功", homeBannerUrl);
    }

    @GetMapping("/new")
    public Result<List<Product>> getNew() {
        List<Product> products = productService.getProducts(4);

        return Result.success("獲取成功", products);
    }

    @GetMapping("/hot")
    public Result<List<Product>> getHot() {
        List<Product> products = productService.getProducts(4);

        return Result.success("獲取成功", products);
    }

    @GetMapping("/head")
    public Result<List<CategoryGoods>> getHead() {
        List<Category> categories = categoryService.getParentCategories();

        List<CategoryGoods> result = new ArrayList<>();
        for (Category category : categories) {
            List<Integer> categoryIds = new ArrayList<>();
            category.getSubCategories().forEach((sub) -> categoryIds.add(sub.getId()));
            List<Product> products = productService.getProductsByCategoryId(categoryIds, 9);
            result.add(new CategoryGoods(category, products));
        }

        return Result.success("獲取成功", result);
    }

    @GetMapping("/goods")
    public Result<List<CategoryGoods>> getGoods() {
        List<Category> categories = categoryService.getParentCategories();

        List<CategoryGoods> result = new ArrayList<>();
        for (Category category : categories) {
            List<Integer> categoryIds = new ArrayList<>();
            category.getSubCategories().forEach((sub) -> categoryIds.add(sub.getId()));
            List<Product> products = productService.getProductsByCategoryId(categoryIds, 8);
            result.add(new CategoryGoods(category, products));
        }

        return Result.success("獲取成功", result);
    }
}
