// Product.java
package com.fluxmall.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Product {
    private Long id;
    private Long memberId;        // 판매자 회원 ID
    private String name;
    private String description;
    private int price;
    private int stockQuantity;
    private String productStatus; // ex: "SALE", "SOLD_OUT", "DISCOUNT" 등
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}