package com.example.commercepjt.product.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class UpdateStockRequest {

    @Max(value = 999999, message = "초기 재고는 999,999개를 초과할 수 없습니다.")
    private int stock;
}
