package com.example.commercepjt.order.dto.response;

import com.example.commercepjt.order.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateOrderResponse {

    private final Long orderId;
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final int quantity;
    private final int totalPrice;
    private final String status;
    private final LocalDateTime orderedAt;
    private final String adminName;

    public CreateOrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.orderNumber = order.getOrderNumber();
        this.customerName = order.getCustomer().getName();
        this.productName = order.getProduct().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus().name();
        this.orderedAt = order.getCreatedAt();
        this.adminName = order.getAdmin() != null ? order.getAdmin().getName() : null;
    }
}