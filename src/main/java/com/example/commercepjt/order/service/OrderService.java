package com.example.commercepjt.order.service;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.common.dto.PageInfo;
import com.example.commercepjt.common.exception.NotFoundException;
import com.example.commercepjt.customer.entity.Customer;
import com.example.commercepjt.customer.repository.CustomerRepository;
import com.example.commercepjt.order.dto.request.CreateOrderRequest;
import com.example.commercepjt.order.dto.response.*;
import com.example.commercepjt.order.entity.Order;
import com.example.commercepjt.order.entity.OrderStatus;
import com.example.commercepjt.order.repository.OrderRepository;
import com.example.commercepjt.product.entity.Product;
import com.example.commercepjt.product.entity.ProductStatus;
import com.example.commercepjt.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    // CS 주문 생성
    @Transactional
    public CreateOrderResponse create(CreateOrderRequest request, Long adminId) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
                () -> new NotFoundException("고객을 찾을 수 없습니다."));

        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new NotFoundException("상품을 찾을 수 없습니다."));

        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new NotFoundException("관리자를 찾을 수 없습니다."));

        if (product.getStatus() == ProductStatus.DISCONTINUED) {
            throw new IllegalArgumentException("단종된 상품은 주문할 수 없습니다.");
        }
        if (product.getStatus() == ProductStatus.SOLD_OUT) {
            throw new IllegalArgumentException("품절된 상품은 주문할 수 없습니다.");
        }

        product.decreaseStock(request.getQuantity());

        Order order = Order.create(customer, product, request.getQuantity(), admin);
        orderRepository.save(order);

        return new CreateOrderResponse(order);
    }

    // 주문 리스트 조회
    @Transactional(readOnly = true)
    public OrderListResponse findAll(String keyword, OrderStatus status, Pageable pageable) {
        Page<Order> orderPage = orderRepository.search(keyword, status, pageable);

        List<OrderResponse> orders = orderPage.getContent().stream()
                .map(OrderResponse::new)
                .toList();

        PageInfo pageInfo = new PageInfo(orderPage);

        return new OrderListResponse(orders, pageInfo);
    }

    // 주문 상세 조회
    @Transactional(readOnly = true)
    public OrderDetailResponse findOne(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("주문을 찾을 수 없습니다."));

        return new OrderDetailResponse(order);
    }

    // 주문 상태 수정
    @Transactional
    public UpdateOrderStatusResponse updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("주문을 찾을 수 없습니다."));

        order.updateStatus(status);

        return new UpdateOrderStatusResponse(order);
    }

    // 주문 취소
    @Transactional
    public void cancel(Long orderId, String cancelReason) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("주문을 찾을 수 없습니다."));

        order.cancel(cancelReason);
        order.getProduct().increaseStock(order.getQuantity());
    }
}