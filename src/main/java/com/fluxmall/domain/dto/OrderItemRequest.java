package com.fluxmall.domain.dto;

/**
 * 바로구매 시 사용할 요청 DTO (컨트롤러에서 받기용)
 */
class OrderItemRequest {
    private Long productId;
    private int quantity;

    // getter, setter, constructor 생략 (실제로는 Lombok @Data 사용 추천)
}