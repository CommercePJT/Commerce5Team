package com.example.commercepjt.product.repository;

import com.example.commercepjt.product.entity.Product;
import com.example.commercepjt.product.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
            "WHERE (:keyword IS NULL OR p.name LIKE %:keyword%) " +
            "AND (:category IS NULL OR p.category = :category) " +
            "AND (:status IS NULL OR p.status = :status)")
    Page<Product> search(@Param("keyword") String keyword,
                         @Param("category") String category,
                         @Param("status") ProductStatus status,
                         Pageable pageable);

    boolean existsByName(String name);

    boolean existsByNameAndProductIdNot(String name, Long productId);

}
