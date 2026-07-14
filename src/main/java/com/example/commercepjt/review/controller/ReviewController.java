package com.example.commercepjt.review.controller;

import com.example.commercepjt.review.dto.request.CreateReviewRequest;
import com.example.commercepjt.review.dto.response.ProductReviewResponse;
import com.example.commercepjt.review.dto.response.ReviewDetailResponse;
import com.example.commercepjt.review.dto.response.ReviewListResponse;
import com.example.commercepjt.review.dto.response.ReviewResponse;
import com.example.commercepjt.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponse> create(
            @Valid @RequestBody CreateReviewRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.create(request));
    }

    // 리뷰 리스트 조회
    @GetMapping("/reviews")
    public ResponseEntity<ReviewListResponse> findAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer rating,
            Pageable pageable) {

        return ResponseEntity.ok(reviewService.findAll(keyword, rating, pageable));
    }

    // 리뷰 상세 조회
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> findOne(
            @PathVariable Long reviewId) {

        return ResponseEntity.ok(reviewService.findOne(reviewId));
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long reviewId) {

        reviewService.delete(reviewId);
        return ResponseEntity.noContent().build();
    }

    // 상품별 리뷰 통계 조회 (도전과제)
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<ProductReviewResponse> findProductReviews(
            @PathVariable Long productId) {

        return ResponseEntity.ok(reviewService.findProductReviews(productId));
    }
}