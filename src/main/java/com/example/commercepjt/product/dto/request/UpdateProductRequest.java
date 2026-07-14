package com.example.commercepjt.product.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateProductRequest {

    @NotBlank(message = "상품명 입력은 필수입니다")
    @Size(max = 100, message = "상품명은 최대 100자까지 입력 가능합니다.")
    private String name;

    @Size(max = 50, message = "카테고리명이 너무 깁니다.")
    @NotBlank(message = "카테고리명 입력은 필수입니다")
    private String category;

    @Max(value = 100000000, message = "가격은 1억 원을 넘을 수 없습니다.")
    @PositiveOrZero(message = "수정할 가격은 0원 이상 이어야합니다.")
    private int price;
}
