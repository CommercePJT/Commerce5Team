package com.example.commercepjt.order.entity;

/** 주문 상태: 준비중 / 배송중 / 배송완료 / 취소됨 */
public enum OrderStatus {
    PREPARING, SHIPPING, DELIVERED, CANCELED;

    /**
     * 상태 전환 가능 여부.
     * 허용: PREPARING -> SHIPPING, SHIPPING -> DELIVERED
     */
    public boolean canTransitionTo(OrderStatus next) {
        return false;
    }
}
