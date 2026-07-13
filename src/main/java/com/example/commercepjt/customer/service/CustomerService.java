package com.example.commercepjt.customer.service;

import com.example.commercepjt.common.dto.PageInfo;
import com.example.commercepjt.customer.dto.CustomerListResponse;
import com.example.commercepjt.customer.dto.CustomerResponse;
import com.example.commercepjt.customer.dto.UpdateCustomerRequest;
import com.example.commercepjt.customer.dto.UpdateCustomerStatusRequest;
import com.example.commercepjt.customer.entity.Customer;
import com.example.commercepjt.customer.entity.CustomerStatus;
import com.example.commercepjt.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    // 고객 상세 조회
    @Transactional(readOnly = true)
    public CustomerResponse findOne(Long customerId) {
        Customer customer = customerOrElseThrow(customerId);
        return new CustomerResponse(customer);
    }

    // 고객 리스트 조회
    @Transactional(readOnly = true)
    public CustomerListResponse findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        List<CustomerResponse> responseList = new ArrayList<>();

        // 페이지 정보와 함께 고객 목록 조회
        Page<Customer> customerPage = customerRepository.findAll(pageable);


        for(Customer customer : customerPage.getContent()) {
            CustomerResponse customerResponse = new CustomerResponse(customer);
            responseList.add(customerResponse);
        }

        // 페이지 정보를 PageInfo DTO에 저장
        PageInfo pageInfo = new PageInfo(
                customerPage.getNumber() + 1,
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages()
        );
        return new CustomerListResponse(responseList, pageInfo);
    }

    // 고객 정보 수정
    @Transactional
    public CustomerResponse update(Long customerId, UpdateCustomerRequest request) {
        Customer customer = customerOrElseThrow(customerId);
        customer.update(request.getName(),
                request.getEmail(),
                request.getPhone());
        return new CustomerResponse(customer);
    }

    // 고객 상태 변경
    @Transactional
    public CustomerResponse updateStatus(
            Long customerId,
            UpdateCustomerStatusRequest request) {
        Customer customer = customerOrElseThrow(customerId);
        customer.updateStatus(request.getStatus());
        return new CustomerResponse(customer);
    }

    // 고객 삭제
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
