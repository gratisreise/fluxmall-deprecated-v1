// Cart.java (장바구니)
package com.fluxmall.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Cart {
    private Long id;
    private Long memberId;        // 회원 1명당 장바구니 1개 (1:1)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}