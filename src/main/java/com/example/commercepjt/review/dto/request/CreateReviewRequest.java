package com.example.commercepjt.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateReviewRequest {

    @NotNull(message = "주문 ID는 필수 입력값입니다")
    private Long orderId;

    @Min(value = 1, message = "평점은 1 이상이어야 합니다")
    @Max(value = 5, message = "평점은 5 이하여야 합니다")
    private int rating;

    @NotBlank(message = "리뷰 내용은 필수 입력값입니다")
    private String content;
}