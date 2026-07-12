package com.example.commercepjt.product.dto;

import com.example.commercepjt.common.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductListResponse {

    private List<ProductResponse> products;
    private PageInfo pageInfo;
}
