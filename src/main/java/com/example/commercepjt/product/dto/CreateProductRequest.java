package com.example.commercepjt.product.dto;

import com.example.commercepjt.product.entity.ProductStatus;
import lombok.Getter;

@Getter
public class CreateProductRequest {

    private String name;
    private String category;
    private int price;
    private int stock;
    private ProductStatus status;
}
