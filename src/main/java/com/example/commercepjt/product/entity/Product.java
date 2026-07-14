package com.example.commercepjt.product.entity;

import com.example.commercepjt.admin.entity.Admin;
import com.example.commercepjt.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    // 상품명
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // 카테고리
    @Column(name = "category", nullable = false)
    private String category;

    // 가격
    @Column(name = "price", nullable = false)
    private int price;

    // 재고
    @Column(name = "stock", nullable = false)
    private int stock;


    // 상품 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    // 등록 관리자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    // ⚠️ 메서드명은 decreaseStock(int) / increaseStock(int)으로 통일
    public Product(
            String name,
            String category,
            int price,
            int stock,
            ProductStatus status,
            Admin admin
    ) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.admin = admin;
    }

    public void update(String name, String category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public void changeStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("재고는 0개 이상이어야 합니다.");
        }
        this.stock = stock;
        updateStatusByStock();
    }

    // 상품 상태 수동 변경 (예: 단종 처리)
    public void changeStatus(ProductStatus status) {
        this.status = status;
    }

    public void decreaseStock(int quantity) {
        validateQuantity(quantity);

        if (this.status == ProductStatus.DISCONTINUED) {
            throw new IllegalStateException("단종된 상품입니다.");
        }
        if (this.status == ProductStatus.SOLD_OUT) {
            throw new IllegalStateException("품절된 상품입니다.");

        }
        if (this.stock < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock -= quantity;
        updateStatusByStock();
    }

    public void increaseStock(int quantity) {
        validateQuantity(quantity);

        this.stock += quantity;
        updateStatusByStock();
    }

    private void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
    }

    // 단종·삭제 상품은 재고가 바뀌어도 상태를 유지한다.
    private void updateStatusByStock() {
        if (this.status == ProductStatus.DISCONTINUED) {
            return;
        }

        this.status = (this.stock == 0)
                ? ProductStatus.SOLD_OUT
                : ProductStatus.ON_SALE;
    }
}
