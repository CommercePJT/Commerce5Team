package com.example.commercepjt.order.dto.response;

import com.example.commercepjt.order.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;

//주문 리스트 조회 응답 항목

@Getter
public class OrderResponse {

    private final Long orderId;
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final int quantity;
    private final int totalPrice;
    private final LocalDateTime orderedAt;
    private final String status;
    private final String cancelReason;
    private final String adminName;

    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.orderNumber = order.getOrderNumber();
        this.customerName = order.getCustomer().getName();
        this.productName = order.getProduct().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.orderedAt = order.getCreatedAt();
        this.status = order.getStatus().name();
        this.cancelReason = order.getCancelReason();
        this.adminName = order.getAdmin() != null ? order.getAdmin().getName() : null;
    }
}