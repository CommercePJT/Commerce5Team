package com.example.commercepjt.order.entity;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.common.entity.BaseEntity;
import com.example.commercepjt.customer.entity.Customer;
import com.example.commercepjt.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")  // 'order'는 SQL 예약어라 복수형 사용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private String cancelReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /** CS 대리 주문의 등록 관리자. 고객 직접 주문이면 null */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    // ===== TODO(담당: 6번 주문 정보 관리) =====

    /** 주문 생성 - 주문번호 생성, 총액 계산(가격 x 수량), 초기 상태 PREPARING */
    public static Order create(Customer customer, Product product, int quantity, Admin admin) {
        return null; // TODO(담당: 6번)
    }

    /** 상태 변경 - canTransitionTo()로 순서 검증 후 변경, 위반 시 IllegalArgumentException */
    public void updateStatus(OrderStatus next) {
        // TODO(담당: 6번)
    }

    /** 주문 취소 - PREPARING만 취소 가능, 상태 CANCELED + 사유 저장 */
    public void cancel(String reason) {
        // TODO(담당: 6번)
    }
}
