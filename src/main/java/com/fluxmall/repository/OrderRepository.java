package com.fluxmall.repository;

import com.fluxmall.mapper.OrderRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;


    public void save(Order order) {
        String sql = "INSERT INTO orders (member_id, order_number, total_price, order_status, shipping_address) " +
                     "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getMemberId(), order.getOrderNumber(),
                order.getTotalPrice(), order.getOrderStatus(), order.getShippingAddress());
    }

    // 방금 삽입된 ID 가져오기 (주문 후 order_item 삽입 시 필요)
    public Long saveAndReturnId(Order order) {
        String sql = "INSERT INTO orders (member_id, order_number, total_price, order_status, shipping_address) " +
                     "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getMemberId(), order.getOrderNumber(),
                order.getTotalPrice(), order.getOrderStatus(), order.getShippingAddress());

        String idSql = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(idSql, Long.class);
    }

    public Order findById(Long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new OrderRowMapper(), id);
    }

    public List<Order> findByMemberId(Long memberId) {
        String sql = "SELECT * FROM orders WHERE member_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new OrderRowMapper(), memberId);
    }
}