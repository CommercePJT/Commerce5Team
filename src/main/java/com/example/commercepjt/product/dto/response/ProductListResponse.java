package com.example.commercepjt.product.dto.response;

import com.example.commercepjt.common.dto.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductListResponse {

    private final List<ProductResponse> products;
    private final PageInfo pageInfo;
}
