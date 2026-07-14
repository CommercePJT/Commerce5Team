package com.example.commercepjt.review.repository;

import com.example.commercepjt.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r " +
            "WHERE (:keyword IS NULL OR r.order.customer.name LIKE %:keyword% " +
            "       OR r.order.product.name LIKE %:keyword%) " +
            "AND (:rating IS NULL OR r.rating = :rating)")
    Page<Review> search(@Param("keyword") String keyword,
                        @Param("rating") Integer rating,
                        Pageable pageable);

    //상품 평균 평점
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.order.product.productId = :productId")
    Double findAverageRating(@Param("productId") Long productId);

   //상품 별점별 개수목록
    @Query("SELECT r.rating, COUNT(r) FROM Review r " +
            "WHERE r.order.product.productId = :productId GROUP BY r.rating")
    List<Object[]> countByRating(@Param("productId") Long productId);

    //상품의 최신 리뷰 3가지
    List<Review> findTop3ByOrderProductProductIdOrderByCreatedAtDesc(Long productId);
}