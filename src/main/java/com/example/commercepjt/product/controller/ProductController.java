package com.example.commercepjt.product.controller;

import com.example.commercepjt.product.dto.CreateProductRequest;
import com.example.commercepjt.product.dto.ProductResponse;
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
            @SessionAttribute("adminId") Long adminId,
            @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(adminId, request));
    }
}
