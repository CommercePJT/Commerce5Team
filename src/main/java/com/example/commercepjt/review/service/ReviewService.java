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
import java.util.LinkedHashMap;
import java.util.Map;

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
//상품별 리뷰 통계 + 최신리뷰 3개
    @Transactional(readOnly = true)
    public ProductReviewResponse getProductReviews(Long productId) {
        // 1. 평균 평점 (리뷰 없으면 null → 0.0 처리, 소수점 1자리 반올림)
        Double average = reviewRepository.findAverageRating(productId);
        double averageRating = (average == null) ? 0.0
                : Math.round(average * 10) / 10.0;

        // 2. 별점별 개수: [rating, count] 목록 → Map으로 변환 (없는 별점은 0)
        Map<Integer, Long> ratingCounts = new LinkedHashMap<>();
        for (int i = 5; i >= 1; i--) {
            ratingCounts.put(i, 0L);
        }
        for (Object[] row : reviewRepository.countByRating(productId)) {
            ratingCounts.put((Integer) row[0], (Long) row[1]);
        }

        // 3. 전체 리뷰 개수 = 별점별 개수의 합
        long totalCount = ratingCounts.values().stream()
                .mapToLong(Long::longValue).sum();

        // 4. 최신 리뷰 3개
        List<ProductReviewResponse.RecentReview> recentReviews =
                reviewRepository.findTop3ByOrderProductProductIdOrderByCreatedAtDesc(productId)
                        .stream()
                        .map(r -> new ProductReviewResponse.RecentReview(
                                r.getOrder().getCustomer().getName(),
                                r.getRating(),
                                r.getContent(),
                                r.getCreatedAt()))
                        .toList();

        return new ProductReviewResponse(averageRating, totalCount, ratingCounts, recentReviews);
    }
}