package com.example.commercepjt.product.dto.response;

import com.example.commercepjt.product.entity.ProductStatus;
import lombok.Getter;

@Getter
public class UpdateProductStatusResponse {

    private final ProductStatus status;

    public UpdateProductStatusResponse(ProductStatus status) {
        this.status = status;
    }
}
