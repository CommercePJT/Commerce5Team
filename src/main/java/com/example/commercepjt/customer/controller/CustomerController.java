package com.example.commercepjt.customer.controller;

import com.example.commercepjt.customer.dto.CustomerResponse;
import com.example.commercepjt.customer.dto.UpdateCustomerRequest;
import com.example.commercepjt.customer.dto.UpdateCustomerStatusRequest;
import com.example.commercepjt.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public CustomerResponse findOne(@PathVariable Long customerId) {
        return customerService.findOne(customerId);
    }

    @PutMapping("/{customerId}")
    public CustomerResponse update(
            @Valid @PathVariable Long customerId,
            @RequestBody UpdateCustomerRequest request
            ) {
        return customerService.update(customerId, request);
    }

    @PatchMapping("/{customerId}/status")
    public CustomerResponse updateStatus(
            @PathVariable Long customerId,
            @RequestBody UpdateCustomerStatusRequest request) {
        return customerService.updateStatus(customerId, request);
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
    }

}
