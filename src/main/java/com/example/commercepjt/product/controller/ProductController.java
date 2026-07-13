package com.example.commercepjt.product.controller;

import com.example.commercepjt.common.exception.UnauthorizedException;
import com.example.commercepjt.product.dto.*;
import com.example.commercepjt.product.entity.ProductStatus;
import com.example.commercepjt.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @RequestBody CreateProductRequest request
    ) {
        checkLogin(adminId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(adminId, request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProduct(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @PathVariable Long productId
    ) {
        checkLogin(adminId);
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @GetMapping
    public ResponseEntity<ProductListResponse> getProducts(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) ProductStatus status
    ) {
        checkLogin(adminId);
        return ResponseEntity.ok(
                productService.getProducts(keyword, page, size, sortBy, direction, category, status)
        );
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @PathVariable Long productId,
            @RequestBody UpdateProductRequest request
    ) {
        checkLogin(adminId);
        return ResponseEntity.ok(
                productService.updateProduct(productId, request)
        );
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<ProductResponse> updateStock(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @PathVariable Long productId,
            @RequestBody UpdateStockRequest request
    ) {
        checkLogin(adminId);
        return ResponseEntity.ok(
                productService.updateStock(productId, request)
        );
    }

    @PatchMapping("/{productId}/status")
    public ResponseEntity<ProductResponse> updateStatus(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @PathVariable Long productId,
            @RequestBody UpdateProductStatusRequest request
    ) {
        checkLogin(adminId);
        return ResponseEntity.ok(
                productService.updateStatus(productId, request)
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @PathVariable Long productId
    ) {
        checkLogin(adminId);
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    private void checkLogin(Long adminId) {
        if (adminId == null) {
            throw new UnauthorizedException("로그인이 필요합니다");
        }
    }
}
