package com.fluxmall.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 주문 상태 흐름
 */
@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING("주문접수"),
    PAID("결제완료"),
    PREPARING("상품준비중"),
    SHIPPED("배송중"),
    DELIVERED("배송완료"),
    CANCELLED("주문취소"),
    REFUNDED("환불완료");

    private final String displayName;

    /**
     * 현재 상태가 '완료' 계열인지 확인 (배송완료, 환불완료 등)
     */
    public boolean isCompleted() {
        return this == DELIVERED || this == REFUNDED;
    }

    /**
     * 현재 상태가 '취소/환불' 계열인지 확인
     */
    public boolean isCancelled() {
        return this == CANCELLED || this == REFUNDED;
    }
}