package com.example.commercepjt.customer.controller;

import com.example.commercepjt.customer.dto.CustomerResponse;
import com.example.commercepjt.customer.dto.UpdateCustomerRequest;
import com.example.commercepjt.customer.dto.UpdateCustomerStatusRequest;
import com.example.commercepjt.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> findOne(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.findOne(customerId));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> update(
            @Valid @PathVariable Long customerId,
            @RequestBody UpdateCustomerRequest request
            ) {
        return ResponseEntity.ok(customerService.update(customerId, request));
    }

    @PatchMapping("/{customerId}/status")
    public ResponseEntity<CustomerResponse> updateStatus(
            @PathVariable Long customerId,
            @RequestBody UpdateCustomerStatusRequest request) {
        return ResponseEntity.ok(customerService.updateStatus(customerId, request));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }

}
