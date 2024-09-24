package com.weihan.ecommerce_backend.controller;

import com.weihan.ecommerce_backend.model.dto.PageResponse;
import com.weihan.ecommerce_backend.model.dto.ProductDetailResponse;
import com.weihan.ecommerce_backend.model.dto.Result;
import com.weihan.ecommerce_backend.model.entity.Category;
import com.weihan.ecommerce_backend.model.entity.Product;
import com.weihan.ecommerce_backend.service.ProductDetailImageService;
import com.weihan.ecommerce_backend.service.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductDetailImageService productDetailImageService;

    public ProductController(ProductService productService, ProductDetailImageService productDetailImageService) {
        this.productService = productService;
        this.productDetailImageService = productDetailImageService;
    }

    @GetMapping("/{id}")
    public Result<ProductDetailResponse> getProductPageDetail(@PathVariable Integer id) {
        Optional<Product> productOpt = productService.findDetailById(id);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            ProductDetailResponse productDetailResponse = new ProductDetailResponse();
            productDetailResponse.setId(product.getId());
            productDetailResponse.setName(product.getName());
            productDetailResponse.setDescription(product.getDescription());
            productDetailResponse.setPrice(product.getPrice());
            productDetailResponse.setImageUrl(product.getImageUrl());
            productDetailResponse.setCreatedDate(product.getCreatedDate());

            // 繞過懶加載創建新物件
            Category parentCategory = new Category(product.getCategory().getParentCategory());
            productDetailResponse.setCategory(List.of(product.getCategory(), parentCategory));

            productDetailResponse.setBrand(product.getBrand());

            List<String> details = productDetailImageService.findByProductId(product.getId());
            productDetailResponse.setDetails(details);

            List<Product> similarProducts = productService.getProductsByCategoryId(product.getCategory().getId(), 6);
            productDetailResponse.setSimilarProducts(similarProducts);

            // 未實現假數據
            productDetailResponse.setSalesCount(100);
            productDetailResponse.setCommentCount(50);
            productDetailResponse.setCollectCount(15);

            return Result.success("獲取成功", productDetailResponse);
        }
        return Result.error("找不到該商品");
    }

    @PostMapping("/search")
    public Result<PageResponse<Product>> getProductsByKeyword(
            @RequestParam String keyword,
            @RequestParam Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "publishTime") String sortField
    ) {
        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.by("id").descending());
        PageResponse<Product> pageResponse = productService.findByNameContaining(keyword, pageable, sortField);

        return Result.success("獲取成功", pageResponse);
    }
}
