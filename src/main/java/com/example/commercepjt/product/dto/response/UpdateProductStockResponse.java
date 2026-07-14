package com.example.commercepjt.product.dto.response;


import lombok.Getter;

@Getter
public class UpdateProductStockResponse {

    private final int stock;
    public UpdateProductStockResponse(int stock) {
        this.stock = stock;
    }
}
