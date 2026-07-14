package com.example.commercepjt.product.controller;

import com.example.commercepjt.common.config.LoginAdmin;
import com.example.commercepjt.product.dto.request.CreateProductRequest;
import com.example.commercepjt.product.dto.request.UpdateProductRequest;
import com.example.commercepjt.product.dto.request.UpdateProductStatusRequest;
import com.example.commercepjt.product.dto.request.UpdateStockRequest;
import com.example.commercepjt.product.dto.response.*;
import com.example.commercepjt.product.entity.ProductStatus;
import com.example.commercepjt.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 등록
    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @LoginAdmin Long adminId,
            @Valid @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.create(adminId, request));
    }

    // 상품 상세조회
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> findOne(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(productService.findOne(productId));
    }

    // 상품 리스트 조회
    @GetMapping
    public ResponseEntity<ProductListResponse> findAll(
            @RequestParam(required = false) String keyword,
                  @RequestParam(required = false) String category,
            @RequestParam(required = false) ProductStatus status, Pageable pageable
    ) {
        return ResponseEntity.ok(productService.findAll(keyword, category, status, pageable)
        );
    }

    // 상품 수정
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        return ResponseEntity.ok(productService.update(productId, request)
        );
    }

    // 상품 재고 수정
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<UpdateProductStockResponse> updateStock(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateStockRequest request
    ) {
        return ResponseEntity.ok(productService.updateStock(productId, request)
        );
    }

    // 상품 상태 수정
    @PatchMapping("/{productId}/status")
    public ResponseEntity<UpdateProductStatusResponse> updateStatus(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductStatusRequest request
    ) {
        return ResponseEntity.ok(productService.updateStatus(productId, request)
        );
    }

    // 상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long productId
    ) {
        productService.delete(productId);

        return ResponseEntity.noContent().build();
    }

}
