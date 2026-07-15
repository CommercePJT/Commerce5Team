package com.example.commercepjt.review.dto.response;

import com.example.commercepjt.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponse {

    private final Long reviewId;
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final int rating;
    private final String content;
    private final LocalDateTime createdAt;

    public ReviewResponse(Review review) {
        this.reviewId = review.getReviewId();
        this.orderNumber = review.getOrder().getOrderNumber();
        this.customerName = review.getOrder().getCustomer().getName();
        this.productName = review.getOrder().getProduct().getName();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}