package com.weihan.ecommerce_backend.service.impl;

import com.weihan.ecommerce_backend.model.entity.ProductDetailImage;
import com.weihan.ecommerce_backend.repository.ProductDetailImageRepository;
import com.weihan.ecommerce_backend.service.ProductDetailImageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDetailImageServiceImpl implements ProductDetailImageService {

    private final ProductDetailImageRepository productDetailImageRepository;

    public ProductDetailImageServiceImpl(ProductDetailImageRepository productDetailImageRepository) {
        this.productDetailImageRepository = productDetailImageRepository;
    }

    @Override
    public List<String> findByProductId(Integer id) {
        List<ProductDetailImage> detailImages = productDetailImageRepository.findByProductId(id);
        List<String> detailImageUrl = new ArrayList<>();
        for (ProductDetailImage detailImage : detailImages) {
            detailImageUrl.add(detailImage.getImageUrl());
        }

        return detailImageUrl;
    }
}
