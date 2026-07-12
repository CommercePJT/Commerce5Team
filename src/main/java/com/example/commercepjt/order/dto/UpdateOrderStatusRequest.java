package com.example.commercepjt.order.dto;

import com.example.commercepjt.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class UpdateOrderStatusRequest {

    private OrderStatus status;
}
