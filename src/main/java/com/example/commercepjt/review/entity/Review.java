package com.example.commercepjt.review.entity;

import com.example.commercepjt.common.entity.BaseEntity;
import com.example.commercepjt.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private int rating;          // 평점 1~5

    @Column(nullable = false, length = 1000)
    private String content;      // 리뷰 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;         // 주문 (고객명·상품명은 order 통해 접근)

    public static Review create(Order order, int rating, String content) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1~5 사이여야 합니다");
        }
        Review review = new Review();
        review.order = order;
        review.rating = rating;
        review.content = content;
        return review;
    }
}