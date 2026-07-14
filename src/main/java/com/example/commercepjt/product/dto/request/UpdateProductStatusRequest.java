package com.example.commercepjt.product.dto.request;

import com.example.commercepjt.product.entity.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateProductStatusRequest {

    @NotNull(message = "ON_SALE | SOLD_OUT | DISCONTINUED 중 하나로 변경하세요.")
    private ProductStatus status;
}
