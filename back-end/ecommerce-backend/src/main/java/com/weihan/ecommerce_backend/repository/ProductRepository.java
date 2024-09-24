package com.weihan.ecommerce_backend.repository;

import com.weihan.ecommerce_backend.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByCategoryId(Integer id, Pageable pageable);

    Page<Product> findByNameContaining(String keyword, Pageable pageable);

    Page<Product> findByCategoryIdIn(List<Integer> ids, Pageable pageable);

    // 使用 EntityGraph 加載懶加載的屬性
    @EntityGraph(attributePaths = {"category", "brand"})
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findDetailById(Integer id);
}
