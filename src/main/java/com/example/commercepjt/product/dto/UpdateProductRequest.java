package com.example.commercepjt.product.dto;

import lombok.Getter;

@Getter
public class UpdateProductRequest {

    private String name;
    private String category;
    private int price;
}
