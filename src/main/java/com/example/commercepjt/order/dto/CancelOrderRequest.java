package com.example.commercepjt.order.dto;

import lombok.Getter;
import jakarta.validation.constraints.NotBlank;

@Getter
public class CancelOrderRequest {

    @NotBlank(message = "취소 사유는 필수 입력값입ㅂ니다")
    private String cancelReason;
}
