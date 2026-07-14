package com.example.commercepjt.review.controller;

import com.example.commercepjt.review.dto.*;
import com.example.commercepjt.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

//리뷰생성(테스트데이터용)
@PostMapping("/reviews")
public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody CreateReviewRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(request));
}

//리뷰리스트조회
@GetMapping("/reviews")
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
@GetMapping("/reviews/{id}")
public ResponseEntity<ReviewDetailResponse> getReview(@PathVariable Long id) {
    return ResponseEntity.ok(reviewService.getReview(id));
}

//리뷰삭제
@DeleteMapping("/reviews/{id}")
public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
    reviewService.deleteReview(id);
    return ResponseEntity.noContent().build();
}
//상품별 리뷰 통계 (도전과제)
    @GetMapping("/products/{id}/reviews")
    public ResponseEntity<ProductReviewResponse> getProductReviews(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getProductReviews(id));
    }
}