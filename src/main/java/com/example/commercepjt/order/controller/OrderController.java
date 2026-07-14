package com.example.commercepjt.order.controller;

import com.example.commercepjt.common.config.LoginAdmin;
import com.example.commercepjt.order.dto.request.CancelOrderRequest;
import com.example.commercepjt.order.dto.request.CreateOrderRequest;
import com.example.commercepjt.order.dto.request.UpdateOrderStatusRequest;
import com.example.commercepjt.order.dto.response.CreateOrderResponse;
import com.example.commercepjt.order.dto.response.OrderDetailResponse;
import com.example.commercepjt.order.dto.response.OrderListResponse;
import com.example.commercepjt.order.entity.OrderStatus;
import com.example.commercepjt.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor

public class OrderController {

    private final OrderService orderService;

    // CS 주문 생성
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(
            @LoginAdmin Long adminId,
            @Valid @RequestBody CreateOrderRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request, adminId));
    }

    // 주문 리스트 조회
    @GetMapping
    public ResponseEntity<OrderListResponse> getOrders(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderStatus status, Pageable pageable
    ) {
        return ResponseEntity.ok(orderService.getOrders(keyword, status, pageable));
    }

    // 주문 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> getOrder(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    // 주문 상태 수정
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request
    ) {
        orderService.updateStatus(id, request.getStatus());

        return ResponseEntity.ok().build();
    }

    // 주문 취소
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long id,
            @Valid @RequestBody CancelOrderRequest request
    ) {
        orderService.cancelOrder(id, request.getCancelReason());

        return ResponseEntity.ok().build();
    }
}