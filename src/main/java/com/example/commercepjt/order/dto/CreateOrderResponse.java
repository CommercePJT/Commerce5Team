package com.example.commercepjt.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateOrderResponse {

    private Long orderId;
    private String orderNumber;
    private String customerName;
    private String productName;
    private int quantity;
    private int totalPrice;
    private String status;
    private LocalDateTime orderedAt;
    private String adminName;

}
