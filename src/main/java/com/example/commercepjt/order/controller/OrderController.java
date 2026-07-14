package com.example.commercepjt.order.controller;

import com.example.commercepjt.order.dto.*;
import com.example.commercepjt.order.entity.OrderStatus;
import com.example.commercepjt.order.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //cs주문생성
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request,
                                                           HttpSession session) {
        Long loginAdminId = (Long) session.getAttribute("adminId");
        CreateOrderResponse response = orderService.createOrder(request, loginAdminId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //주문리스트조회
    @GetMapping
    public ResponseEntity<OrderListResponse> getOrders(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrders(keyword, page, size, sortBy, direction, status));
    }

    //주문상세조회
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    //주문상태수정
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @Valid @RequestBody UpdateOrderStatusRequest request) {
        orderService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok().build();
    }

    //주문취소
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id,
                                            @Valid @RequestBody CancelOrderRequest request) {
        orderService.cancelOrder(id, request.getCancelReason());
        return ResponseEntity.ok().build();
    }
}
