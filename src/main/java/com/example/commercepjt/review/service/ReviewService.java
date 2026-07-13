package com.example.commercepjt.review.service;

import com.example.commercepjt.common.dto.PageInfo;
import com.example.commercepjt.common.exception.NotFoundException;
import com.example.commercepjt.order.entity.Order;
import com.example.commercepjt.order.repository.OrderRepository;
import com.example.commercepjt.review.dto.*;
import com.example.commercepjt.review.entity.Review;
import com.example.commercepjt.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

//리뷰생성(테스트데이터용)
    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 주문입니다"));
        Review review = Review.create(order, request.getRating(), request.getContent());
        reviewRepository.save(review);
        return ReviewResponse.from(review);
    }

//리뷰리스트조회
    @Transactional(readOnly = true)
    public ReviewListResponse getReviews(String keyword, int page, int size,
                                         String sortBy, String direction, Integer rating) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Review> reviewPage = reviewRepository.search(keyword, rating, pageable);

        List<ReviewResponse> reviews = reviewPage.getContent().stream()
                .map(ReviewResponse::from)
                .toList();

        PageInfo pageInfo = new PageInfo(page, size,
                reviewPage.getTotalElements(), reviewPage.getTotalPages());

        return new ReviewListResponse(reviews, pageInfo);
    }

//리뷰상세조회
    @Transactional(readOnly = true)
    public ReviewDetailResponse getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 리뷰입니다"));
        return ReviewDetailResponse.from(review);
    }

//리뷰삭제
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 리뷰입니다"));
        reviewRepository.delete(review);
    }
}