// Product.java
package com.fluxmall.model;

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
    private Integer price;        // NULL 허용이지만 Integer로 (price > 0 체크는 DB에서)
    private int stockQuantity;
    private String productStatus; // ex: "SALE", "SOLD_OUT", "DISCOUNT" 등
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}