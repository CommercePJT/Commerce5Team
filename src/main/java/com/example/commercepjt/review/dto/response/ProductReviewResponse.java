package com.example.commercepjt.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

//상품별 리뷰통계 + 최신리뷰 응답
@Getter
@AllArgsConstructor
public class ProductReviewResponse {

    private double averageRating;              // 평균 평점 (소수점 1자리)
    private long totalReviewCount;             // 전체 리뷰 개수
    private Map<Integer, Long> ratingCounts;   // 별점별 개수 {5: 2, 4: 1, ...}
    private List<RecentReview> recentReviews;  // 최신 리뷰 3개

    @Getter
    @AllArgsConstructor
    public static class RecentReview {
        private String customerName;
        private int rating;
        private String content;
        private java.time.LocalDateTime createdAt;
    }
}