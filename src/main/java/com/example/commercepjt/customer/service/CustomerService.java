package com.example.commercepjt.customer.service;

import com.example.commercepjt.customer.dto.CustomerResponse;
import com.example.commercepjt.customer.dto.UpdateCustomerRequest;
import com.example.commercepjt.customer.dto.UpdateCustomerStatusRequest;
import com.example.commercepjt.customer.entity.Customer;
import com.example.commercepjt.customer.entity.CustomerStatus;
import com.example.commercepjt.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public CustomerResponse findOne(Long customerId) {
        Customer customer = customerOrElseThrow(customerId);
        return new CustomerResponse(customer);
    }

    @Transactional
    public CustomerResponse update(Long customerId, UpdateCustomerRequest request) {
        Customer customer = customerOrElseThrow(customerId);
        customer.update(request.getName(),
                request.getEmail(),
                request.getPhone());
        return new CustomerResponse(customer);
    }

    @Transactional
    public CustomerResponse updateStatus(
            Long customerId,
            UpdateCustomerStatusRequest request) {
        Customer customer = customerOrElseThrow(customerId);
        customer.updateStatus(request.getStatus());
        return new CustomerResponse(customer);
    }

    @Transactional
    public void delete(Long customerId) {
        Customer customer = customerOrElseThrow(customerId);
        customerRepository.delete(customer);
    }

    private Customer customerOrElseThrow(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new IllegalStateException("고객이 존재하지 않습니다.")
                );
    }
}
