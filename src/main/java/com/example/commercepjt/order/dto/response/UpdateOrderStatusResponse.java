package com.example.commercepjt.order.dto.response;

import com.example.commercepjt.order.entity.Order;
import lombok.Getter;

@Getter
public class UpdateOrderStatusResponse {

    private final Long orderId;
    private final String orderNumber;
    private final String status;

    public UpdateOrderStatusResponse(Order order) {
        this.orderId = order.getOrderId();
        this.orderNumber = order.getOrderNumber();
        this.status = order.getStatus().name();
    }
}