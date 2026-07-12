package com.example.commercepjt.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/** 주문 상세 조회 응답 - 고객 직접 주문이면 admin 관련 필드는 null */
@Getter
@AllArgsConstructor
public class OrderDetailResponse {

    private String orderNumber;
    private String customerName;
    private String customerEmail;
    private String productName;
    private int quantity;
    private int totalPrice;
    private LocalDateTime orderedAt;
    private String status;
    private String adminName;
    private String adminEmail;
    private String adminRole;

}
