package com.example.commercepjt.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/** 주문 리스트 조회 응답 항목 */
@Getter
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private String orderNumber;
    private String customerName;
    private String productName;
    private int quantity;
    private int totalPrice;
    private LocalDateTime orderedAt;
    private String status;
    private String adminName;

}
