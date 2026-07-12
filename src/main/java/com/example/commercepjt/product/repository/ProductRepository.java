package com.example.commercepjt.product.repository;

import com.example.commercepjt.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
