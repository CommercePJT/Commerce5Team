package com.example.commercepjt.product.controller;

import com.example.commercepjt.product.dto.request.CreateProductRequest;
import com.example.commercepjt.product.dto.request.UpdateProductRequest;
import com.example.commercepjt.product.dto.request.UpdateProductStatusRequest;
import com.example.commercepjt.product.dto.request.UpdateStockRequest;
import com.example.commercepjt.product.dto.response.*;
import com.example.commercepjt.product.entity.ProductStatus;
import com.example.commercepjt.product.service.ProductService;
import jakarta.validation.Valid;
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
            @SessionAttribute(name = "adminId") Long adminId,
            @Valid @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(adminId, request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProduct(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @PathVariable Long productId
    ) {
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
        return ResponseEntity.ok(
                productService.getProducts(keyword, page, size, sortBy, direction, category, status)
        );
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        return ResponseEntity.ok(
                productService.updateProduct(productId, request)
        );
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<UpdateProductStockResponse> updateStock(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateStockRequest request
    ) {
        return ResponseEntity.ok(
                productService.updateStock(productId, request)
        );
    }

    @PatchMapping("/{productId}/status")
    public ResponseEntity<UpdateProductStatusResponse> updateStatus(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductStatusRequest request
    ) {
        return ResponseEntity.ok(
                productService.updateStatus(productId, request)
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @SessionAttribute(name = "adminId", required = false) Long adminId,
            @PathVariable Long productId
    ) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
