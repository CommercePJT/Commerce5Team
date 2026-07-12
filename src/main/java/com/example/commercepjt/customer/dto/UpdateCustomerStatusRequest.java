package com.example.commercepjt.customer.dto;

import com.example.commercepjt.customer.entity.CustomerStatus;
import lombok.Getter;

@Getter
public class UpdateCustomerStatusRequest {

    private CustomerStatus status;
}
