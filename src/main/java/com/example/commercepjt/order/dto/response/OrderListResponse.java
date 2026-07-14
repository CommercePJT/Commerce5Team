package com.example.commercepjt.order.dto.response;

import com.example.commercepjt.common.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderListResponse {

    private List<OrderResponse> orders;
    private PageInfo pageInfo;
}
