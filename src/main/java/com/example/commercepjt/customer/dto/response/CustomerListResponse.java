package com.example.commercepjt.customer.dto.response;

import com.example.commercepjt.common.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomerListResponse {

    private final List<CustomerResponse> customers;
    private final PageInfo pageInfo;
}
