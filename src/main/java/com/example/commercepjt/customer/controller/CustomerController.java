package com.example.commercepjt.customer.controller;

import com.example.commercepjt.customer.dto.CustomerListResponse;
import com.example.commercepjt.customer.dto.CustomerResponse;
import com.example.commercepjt.customer.dto.UpdateCustomerRequest;
import com.example.commercepjt.customer.dto.UpdateCustomerStatusRequest;
import com.example.commercepjt.customer.entity.CustomerStatus;
import com.example.commercepjt.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // 고객 상세 조회
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> findOne(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.findOne(customerId));
    }

    // 고객 리스트 조회
    @GetMapping
    public ResponseEntity<CustomerListResponse> findAll(
            @RequestParam(required = false) String keyword,              // 검색어 없어도 요청 가능
            @RequestParam(required = false) CustomerStatus status,       // 상태 필터 없어도 요청 가능
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        return ResponseEntity.ok(customerService.findAll(
                keyword, status, page, size, sortBy, sortDir));
    }

    // 고객 정보 수정
    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> update(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerRequest request
            ) {
        return ResponseEntity.ok(customerService.update(customerId, request));
    }

    // 고객 상태 변경
    @PatchMapping("/{customerId}/status")
    public ResponseEntity<CustomerResponse> updateStatus(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerStatusRequest request) {
        return ResponseEntity.ok(customerService.updateStatus(customerId, request));
    }

    // 고객 삭제
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }

}
