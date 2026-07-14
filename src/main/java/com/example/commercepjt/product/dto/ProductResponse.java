package com.example.commercepjt.product.dto;
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

    private Long id;
    private String name;
    private String category;
    private int price;
    private int stock;
    private String status;
    private LocalDateTime createdAt;
    private String adminName;

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
