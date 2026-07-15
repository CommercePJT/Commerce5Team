package com.example.commercepjt.review.dto.response;

import com.example.commercepjt.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDetailResponse {

    private final String productName;  // 상품상태
    private final String customerName;
    private final String customerEmail;
    private final LocalDateTime createdAt;
    private final int rating;
    private final String content;

    public ReviewDetailResponse(Review review) {
        this.productName = review.getOrder().getProduct().getName();
        this.customerName = review.getOrder().getCustomer().getName();
        this.customerEmail = review.getOrder().getCustomer().getEmail();
        this.createdAt = review.getCreatedAt();
        this.rating = review.getRating();
        this.content = review.getContent();
    }
}