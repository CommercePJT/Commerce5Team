package com.example.commercepjt.order.controller;

import com.example.commercepjt.common.config.LoginAdmin;
import com.example.commercepjt.order.dto.request.CancelOrderRequest;
import com.example.commercepjt.order.dto.request.CreateOrderRequest;
import com.example.commercepjt.order.dto.request.UpdateOrderStatusRequest;
import com.example.commercepjt.order.dto.response.CreateOrderResponse;
import com.example.commercepjt.order.dto.response.OrderDetailResponse;
import com.example.commercepjt.order.dto.response.OrderListResponse;
import com.example.commercepjt.order.dto.response.UpdateOrderStatusResponse;
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
    public ResponseEntity<CreateOrderResponse> create(
            @LoginAdmin Long adminId,
            @Valid @RequestBody CreateOrderRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request, adminId));
    }

    // 주문 리스트 조회
    @GetMapping
    public ResponseEntity<OrderListResponse> findAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderStatus status,
            Pageable pageable) {

        return ResponseEntity.ok(orderService.findAll(keyword, status, pageable));
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponse> findOne(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.findOne(orderId));
    }

    // 주문 상태 수정
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<UpdateOrderStatusResponse> updateStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {

        return ResponseEntity.ok(orderService.updateStatus(orderId, request.getStatus()));
    }

    // 주문 취소
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancel(
            @PathVariable Long orderId,
            @Valid @RequestBody CancelOrderRequest request) {

        orderService.cancel(orderId, request.getCancelReason());
        return ResponseEntity.noContent().build();
    }
}