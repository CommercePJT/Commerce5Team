package com.example.commercepjt.review.dto;

import com.example.commercepjt.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewDetailResponse {

    private String productName;
    private String customerName;
    private String customerEmail;
    private LocalDateTime createdAt;
    private int rating;
    private String content;

    public static ReviewDetailResponse from(Review review) {
        return new ReviewDetailResponse(
                review.getOrder().getProduct().getName(),
                review.getOrder().getCustomer().getName(),
                review.getOrder().getCustomer().getEmail(),
                review.getCreatedAt(),
                review.getRating(),
                review.getContent()
        );
    }
}