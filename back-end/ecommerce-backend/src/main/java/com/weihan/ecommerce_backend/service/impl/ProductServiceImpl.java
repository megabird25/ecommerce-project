package com.weihan.ecommerce_backend.service.impl;

import com.weihan.ecommerce_backend.model.dto.PageResponse;
import com.weihan.ecommerce_backend.model.entity.Product;
import com.weihan.ecommerce_backend.repository.ProductRepository;
import com.weihan.ecommerce_backend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public PageResponse<Product> findByCategoryId(Integer id, Pageable pageable, String sortField) {
        Page<Product> products = productRepository.findByCategoryId(id, pageable);

        return createPageResponseOfProduct(products);
    }

    @Override
    public List<Product> getProductsByCategoryId(Integer id, Integer num) {
        Pageable pageable = PageRequest.of(1, num);

        return productRepository.findByCategoryId(id, pageable).getContent();
    }

    @Override
    public Optional<Product> findDetailById(Integer id) {
        return productRepository.findDetailById(id);
    }

    @Override
    public PageResponse<Product> findByNameContaining(String keyword, Pageable pageable, String sortField) {
        Page<Product> products = productRepository.findByNameContaining(keyword, pageable);

        return createPageResponseOfProduct(products);
    }

    @Override
    public List<Product> getProducts(Integer num) {
        Pageable pageable = PageRequest.of(1, num);

        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Product> getProductsByCategoryId(List<Integer> ids, Integer num) {
        Pageable pageable = PageRequest.of(1, num);

        return productRepository.findByCategoryIdIn(ids, pageable).getContent();
    }

    private PageResponse<Product> createPageResponseOfProduct(Page<Product> products) {
        PageResponse<Product> pageResponse = new PageResponse<>();

        pageResponse.setPage(products.getNumber()+1);
        pageResponse.setPageSize(products.getSize());
        pageResponse.setTotal(products.getTotalElements());
        pageResponse.setTotalPages(products.getTotalPages());
        pageResponse.setData(products.getContent());

        return pageResponse;
    }
}
