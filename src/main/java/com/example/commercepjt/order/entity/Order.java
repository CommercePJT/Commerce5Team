package com.example.commercepjt.order.entity;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.common.entity.BaseEntity;
import com.example.commercepjt.customer.entity.Customer;
import com.example.commercepjt.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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

//주문생성
    public static Order create(Customer customer, Product product, int quantity, Admin admin) {
        Order order = new Order();
        order.orderNumber = generateOrderNumber();
        order.quantity = quantity;
        order.totalPrice = product.getPrice() * quantity;  // 주문 당시 가격 기준
        order.status = OrderStatus.PREPARING;
        order.customer = customer;
        order.product = product;
        order.admin = admin;
        return order;
    }

    private static String generateOrderNumber() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ORD-" + date + "-" + random;
    }
//주문상태변경
    public void updateStatus(OrderStatus next) {
        if (!this.status.canTransitionTo(next)) {
            throw new IllegalArgumentException(
                    "주문 상태를 " + this.status + "에서 " + next + "(으)로 변경할 수 없습니다");
        }
        this.status = next;
    }
//주문취소
    public void cancel(String reason) {
        if (this.status != OrderStatus.PREPARING) {
            throw new IllegalArgumentException("준비중 상태의 주문만 취소할 수 있습니다");
        }
        this.status = OrderStatus.CANCELED;
        this.cancelReason = reason;
    }
}
