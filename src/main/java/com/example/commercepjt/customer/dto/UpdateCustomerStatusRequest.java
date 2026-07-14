package com.example.commercepjt.customer.dto;

import com.example.commercepjt.customer.entity.CustomerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateCustomerStatusRequest {

    @NotNull(message = "고객 상태는 필수입니다.")
    private CustomerStatus status;
}
