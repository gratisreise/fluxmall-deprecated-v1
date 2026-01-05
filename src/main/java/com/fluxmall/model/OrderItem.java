// OrderItem.java (주문 상품)
package com.fluxmall.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class OrderItem {
    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;
    private int price;            // 주문 시점의 상품 가격 (스냅샷)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}