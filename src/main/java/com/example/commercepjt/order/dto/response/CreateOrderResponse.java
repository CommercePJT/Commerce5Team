package com.example.commercepjt.order.dto.response;

import com.example.commercepjt.order.entity.Order;
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

    public static CreateOrderResponse from(Order order) {
        return new CreateOrderResponse(
                order.getOrderId(),
                order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getCreatedAt(),
                order.getAdmin() !=null ? order.getAdmin().getName() : null);
}


}
