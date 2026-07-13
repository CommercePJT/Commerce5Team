package com.example.commercepjt.review.repository;

import com.example.commercepjt.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r " +
            "WHERE (:keyword IS NULL OR r.order.customer.name LIKE %:keyword% " +
            "       OR r.order.product.productName LIKE %:keyword%) " +
            "AND (:rating IS NULL OR r.rating = :rating)")
    Page<Review> search(@Param("keyword") String keyword,
                        @Param("rating") Integer rating,
                        Pageable pageable);
}