package com.example.commercepjt.review.controller;

import com.example.commercepjt.review.dto.*;
import com.example.commercepjt.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

//리뷰생성(테스트데이터용)
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody CreateReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(request));
    }

//리뷰리스트조회
    @GetMapping
    public ResponseEntity<ReviewListResponse> getReviews(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) Integer rating) {
        return ResponseEntity.ok(reviewService.getReviews(keyword, page, size, sortBy, direction, rating));
    }

//리뷰상세조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDetailResponse> getReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReview(reviewId));
    }

//리뷰삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}