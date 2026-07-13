package com.example.commercepjt.order.service;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.customer.entity.Customer;
import com.example.commercepjt.customer.repository.CustomerRepository;
import com.example.commercepjt.order.dto.*;
import com.example.commercepjt.order.repository.OrderRepository;
import com.example.commercepjt.product.entity.Product;
import com.example.commercepjt.product.entity.ProductStatus;
import com.example.commercepjt.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.common.dto.PageInfo;
import com.example.commercepjt.common.exception.NotFoundException;
import com.example.commercepjt.customer.repository.CustomerRepository;
import com.example.commercepjt.order.entity.Order;
import com.example.commercepjt.order.entity.OrderStatus;
import com.example.commercepjt.order.repository.OrderRepository;
import com.example.commercepjt.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional(readOnly = true)
    public OrderListResponse getOrders(String keyword, int page, int size,
                                       String sortBy, String direction, OrderStatus status) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Order> orderPage = orderRepository.search(keyword, status, pageable);

        List<OrderResponse> orders = orderPage.getContent().stream()
                .map(OrderResponse::from)
                .toList();

        PageInfo pageInfo = new PageInfo(
                page,
                size,
                orderPage.getTotalElements(),
                orderPage.getTotalPages()
        );

        return new OrderListResponse(orders, pageInfo);
    }
    @Transactional(readOnly = true)
    public OrderDetailResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 주문입니다"));
        return OrderDetailResponse.from(order);
    }
    @Transactional
    public void updateStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 주문입니다"));
        order.updateStatus(status);
    }
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request, Long loginAdminId) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 상품입니다"));
        Admin admin = adminRepository.findById(loginAdminId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 관리자입니다"));

        if (product.getStatus() == ProductStatus.DISCONTINUED) {
            throw new IllegalArgumentException("단종된 상품은 주문할 수 없습니다");
        }
        if (product.getStatus() == ProductStatus.SOLD_OUT) {
            throw new IllegalArgumentException("품절된 상품은 주문할 수 없습니다");
        }

        product.decreaseStock(request.getQuantity());

        Order order = Order.create(customer, product, request.getQuantity(), admin);
        orderRepository.save(order);

        return CreateOrderResponse.from(order);
    }

    @Transactional
    public void cancelOrder(Long orderId, String cancelReason) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 주문입니다"));

        order.cancel(cancelReason);
        order.getProduct().increaseStock(order.getQuantity());
    }
}
