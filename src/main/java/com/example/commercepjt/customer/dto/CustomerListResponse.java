package com.example.commercepjt.customer.dto;

import com.example.commercepjt.common.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomerListResponse {

    private List<CustomerResponse> customers;
    private PageInfo pageInfo;
}
