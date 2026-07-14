package com.example.commercepjt.product.dto.response;

import com.example.commercepjt.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 상품 등록/리스트 조회 응답 항목
 */
@Getter
@AllArgsConstructor
public class ProductResponse {

    private final Long id;
    private final String name;
    private final String category;
    private final int price;
    private final int stock;
    private final String status;
    private final LocalDateTime createdAt;
    private final String adminName;

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus().name(),
                product.getCreatedAt(),
                product.getAdmin().getName()
        );
    }
}
