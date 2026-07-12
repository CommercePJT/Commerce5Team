package com.example.commercepjt.order.dto;

import lombok.Getter;

@Getter
public class CreateOrderRequest {

    private Long customerId;
    private Long productId;
    private int quantity;
}
