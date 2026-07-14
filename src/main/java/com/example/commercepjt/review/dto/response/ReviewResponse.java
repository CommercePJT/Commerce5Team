package com.example.commercepjt.review.dto.response;

import com.example.commercepjt.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewResponse {

    private Long reviewId;
    private String orderNumber;
    private String customerName;
    private String productName;
    private int rating;
    private String content;
    private LocalDateTime createdAt;

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getReviewId(),
                review.getOrder().getOrderNumber(),
                review.getOrder().getCustomer().getName(),
                review.getOrder().getProduct().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}