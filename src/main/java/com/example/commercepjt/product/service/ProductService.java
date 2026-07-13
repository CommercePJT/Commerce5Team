package com.example.commercepjt.product.service;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.common.exception.NotFoundException;
import com.example.commercepjt.product.dto.CreateProductRequest;
import com.example.commercepjt.product.dto.ProductDetailResponse;
import com.example.commercepjt.product.dto.ProductResponse;
import com.example.commercepjt.product.dto.UpdateProductRequest;
import com.example.commercepjt.product.dto.UpdateProductStatusRequest;
import com.example.commercepjt.product.dto.UpdateStockRequest;
import com.example.commercepjt.product.entity.Product;
import com.example.commercepjt.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    public ProductResponse createProduct(Long adminId, CreateProductRequest request) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("관리자를 찾을 수 없습니다."));

        Product product = new Product(
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getStock(),
                request.getStatus(),
                admin
        );

        Product savedProduct = productRepository.save(product);

        return toProductResponse(savedProduct);
    }


    private ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus().name(),
                product.getCreatedAt(),
                product.getAdmin().getName()
        );
    }
}