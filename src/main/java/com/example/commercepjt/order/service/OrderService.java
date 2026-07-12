package com.example.commercepjt.order.service;

import com.example.commercepjt.admin.repository.AdminRepository;
import com.example.commercepjt.customer.repository.CustomerRepository;
import com.example.commercepjt.order.repository.OrderRepository;
import com.example.commercepjt.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;


}
