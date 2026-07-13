package com.example.commercepjt.order.dto;

import lombok.Getter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter
public class CreateOrderRequest {

    @NotNull(message = "고객 ID는 필수 입력값입니다")
    private Long customerId;

    @NotNull(message = "상품 ID는 필수 입력값입니다")
    private Long productId;

    @Min(value = 1, message = "수량은 1개 이상이어야 합니다")
    private int quantity;
}
