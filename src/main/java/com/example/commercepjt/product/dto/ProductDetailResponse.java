package com.example.commercepjt.product.dto;

import com.example.commercepjt.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 상품 상세 조회 응답 - 리스트 항목 + 등록 관리자 이메일
 */
@Getter
@AllArgsConstructor
public class ProductDetailResponse {

    private String name;
    private String category;
    private int price;
    private int stock;
    private String status;
    private LocalDateTime createdAt;
    private String adminName;
    private String adminEmail;

    public static ProductDetailResponse from(Product product) {
        return new ProductDetailResponse(
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus().name(),
                product.getCreatedAt(),
                product.getAdmin().getName(),
                product.getAdmin().getEmail()
        );
    }
}
