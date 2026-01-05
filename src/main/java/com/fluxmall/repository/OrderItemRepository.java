package com.fluxmall.repository;


import com.fluxmall.mapper.OrderItemRowMapper;
import com.fluxmall.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(OrderItem orderItem) {
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getProductId(),
                orderItem.getQuantity(), orderItem.getPrice());
    }

    // 주문에 속한 모든 항목 조회
    public List<OrderItem> findByOrderId(Long orderId) {
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        return jdbcTemplate.query(sql, new OrderItemRowMapper(), orderId);
    }
}