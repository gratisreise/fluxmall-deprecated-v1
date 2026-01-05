// Order.java (주문)
package com.fluxmall.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Order {
    private Long id;
    private Long memberId;
    private String orderNumber;   // UNIQUE
    private Integer totalPrice;   // 계산된 총 금액 (NULL 가능 → 주문 시 계산)
    private String orderStatus;   // ex: "PAYMENT_WAITING", "PREPARING", "SHIPPED" 등
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}