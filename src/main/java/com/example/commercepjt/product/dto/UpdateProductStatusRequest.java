package com.example.commercepjt.product.dto;

import com.example.commercepjt.product.entity.ProductStatus;
import lombok.Getter;

@Getter
public class UpdateProductStatusRequest {

    private ProductStatus status;
}
