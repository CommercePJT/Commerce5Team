package com.example.commercepjt.product.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class UpdateStockRequest {

    @Max(value = 999999, message = "초기 재고는 999,999개를 초과할 수 없습니다.")
    @PositiveOrZero(message = "초기 재고 입력0개 이상이어야 합니다")
    private int stock;
}
