package com.example.commercepjt.review.service;

import com.example.commercepjt.common.dto.PageInfo;
import com.example.commercepjt.common.exception.NotFoundException;
import com.example.commercepjt.order.entity.Order;
import com.example.commercepjt.order.repository.OrderRepository;
import com.example.commercepjt.review.dto.request.CreateReviewRequest;
import com.example.commercepjt.review.dto.response.ProductReviewResponse;
import com.example.commercepjt.review.dto.response.ReviewDetailResponse;
import com.example.commercepjt.review.dto.response.ReviewListResponse;
import com.example.commercepjt.review.dto.response.ReviewResponse;
import com.example.commercepjt.review.entity.Review;
import com.example.commercepjt.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    // 리뷰 생성
    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 주문입니다"));

        Review review = Review.create(order, request.getRating(), request.getContent());
        reviewRepository.save(review);

        return ReviewResponse.from(review);
    }

    // 리뷰 리스트 조회
    @Transactional(readOnly = true)
    public ReviewListResponse getReviews(String keyword, Integer rating, Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.search(keyword, rating, pageable);

        List<ReviewResponse> reviews = reviewPage.getContent().stream()
                .map(ReviewResponse::from)
                .toList();

        PageInfo pageInfo = new PageInfo(
                reviewPage.getNumber() + 1,
                reviewPage.getSize(),
                reviewPage.getTotalElements(),
                reviewPage.getTotalPages()
        );

        return new ReviewListResponse(reviews, pageInfo);
    }

    // 리뷰 상세 조회
    @Transactional(readOnly = true)
    public ReviewDetailResponse getReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 리뷰입니다"));

        return ReviewDetailResponse.from(review);
    }

    // 리뷰 삭제
    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 리뷰입니다"));

        reviewRepository.delete(review);
    }

    // 상품별 리뷰 통계 조회
    @Transactional(readOnly = true)
    public ProductReviewResponse getProductReviews(Long productId) {
        Double average = reviewRepository.findAverageRating(productId);
        double averageRating = (average == null) ? 0.0 : Math.round(average * 10) / 10.0;

        Map<Integer, Long> ratingCounts = new LinkedHashMap<>();
        for (int i = 5; i >= 1; i--) {
            ratingCounts.put(i, 0L);
        }
        for (Object[] row : reviewRepository.countByRating(productId)) {
            ratingCounts.put((Integer) row[0], (Long) row[1]);
        }

        long totalCount = ratingCounts.values().stream()
                .mapToLong(Long::longValue)
                .sum();

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