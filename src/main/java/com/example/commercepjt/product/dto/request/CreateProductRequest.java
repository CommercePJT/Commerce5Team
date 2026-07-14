package com.example.commercepjt.product.dto.request;

import com.example.commercepjt.product.entity.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class CreateProductRequest {

    @NotBlank(message = "상품명 입력은 필수입니다")
    @Size(max = 100, message = "상품명은 최대 100자까지 입력 가능합니다.")
    private String name;

    @Size(max = 50, message = "카테고리명이 너무 깁니다.")
    @NotBlank(message = "카테고리명 입력은 필수입니다")
    private String category;

    @Max(value = 100000000, message = "가격은 1억 원을 넘을 수 없습니다.")
    @PositiveOrZero(message = "초기가격은 0원 이상 이어야합니다.")
    private int price;

    @Max(value = 999999, message = "초기 재고는 999,999개를 초과할 수 없습니다.")
    @PositiveOrZero(message = "초기 재고 입력0개 이상이어야 합니다")
    private int stock;

    @NotNull(message = "ON_SALE | SOLD_OUT | DISCONTINUED 중 하나로 입력하세요")
    private ProductStatus status;
}
