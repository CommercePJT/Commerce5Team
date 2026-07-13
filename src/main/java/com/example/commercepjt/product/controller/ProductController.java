package com.example.commercepjt.product.controller;

import com.example.commercepjt.product.dto.*;
import com.example.commercepjt.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @SessionAttribute("adminId") Long adminId,
            @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(adminId, request));
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProduct(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody UpdateProductRequest request
    ) {
        return ResponseEntity.ok(
                productService.updateProduct(productId, request)
        );
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<ProductResponse> updateStock(
            @PathVariable Long productId,
            @RequestBody UpdateStockRequest request
    ) {
        return ResponseEntity.ok(
                productService.updateStock(productId, request)
        );
    }

    @PatchMapping("/{productId}/status")
    public ResponseEntity<ProductResponse> updateStatus(
            @PathVariable Long productId,
            @RequestBody UpdateProductStatusRequest request
    ) {
        return ResponseEntity.ok(
                productService.updateStatus(productId, request)
        );
    }




}
