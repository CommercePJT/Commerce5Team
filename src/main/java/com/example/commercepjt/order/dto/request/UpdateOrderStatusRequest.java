package com.example.commercepjt.order.dto.request;

import com.example.commercepjt.order.entity.OrderStatus;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;

@Getter
public class UpdateOrderStatusRequest {

    @NotNull(message = "상태는 필수 선택값입니다")
    private OrderStatus status;
}