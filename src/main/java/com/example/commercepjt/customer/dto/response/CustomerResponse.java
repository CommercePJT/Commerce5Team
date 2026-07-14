package com.example.commercepjt.customer.dto.response;

import com.example.commercepjt.customer.entity.Customer;
import com.example.commercepjt.customer.entity.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

//고객 리스트,상세 조회 응답 항목
@Getter
@AllArgsConstructor
public class CustomerResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final CustomerStatus status;
    private final LocalDateTime createdAt;

    public CustomerResponse(Customer customer) {
        this.id = customer.getCustomerId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.status = customer.getStatus();
        this.createdAt = customer.getCreatedAt();
    }
}
