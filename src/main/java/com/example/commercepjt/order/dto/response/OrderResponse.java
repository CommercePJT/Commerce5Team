package com.example.commercepjt.order.dto.response;

import com.example.commercepjt.order.entity.Order;
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
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                order.getStatus().name(),
                order.getAdmin() != null ? order.getAdmin().getName() : null
        );
    }
}