package com.example.commercepjt.order.dto.response;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.order.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;

//주문 상세 조회 응답 - 고객 직접 주문이면 admin 관련 필드는 null

@Getter
public class OrderDetailResponse {

    private final String orderNumber;
    private final String customerName;
    private final String customerEmail;
    private final String productName;
    private final int quantity;
    private final int totalPrice;
    private final LocalDateTime orderedAt;
    private final String status;
    private final String adminName;
    private final String adminEmail;
    private final String adminRole;

    public OrderDetailResponse(Order order) {
        Admin admin = order.getAdmin();

        this.orderNumber = order.getOrderNumber();
        this.customerName = order.getCustomer().getName();
        this.customerEmail = order.getCustomer().getEmail();
        this.productName = order.getProduct().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.orderedAt = order.getCreatedAt();
        this.status = order.getStatus().name();
        this.adminName = admin != null ? admin.getName() : null;
        this.adminEmail = admin != null ? admin.getEmail() : null;
        this.adminRole = admin != null ? admin.getRole().name() : null;
    }
}