package com.fluxmall.dao;

import com.fluxmall.domain.mapper.OrderRowMapper;
import com.fluxmall.domain.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(OrderVO order) {
        String sql = "INSERT INTO orders (member_id, order_number, product_id, quantity, total_price, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getMemberId(), order.getOrderNumber(),
                order.getProductId(), order.getQuantity(), order.getTotalPrice(), order.getStatus());
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    public OrderVO findById(Long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new OrderRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<OrderVO> findByMemberId(Long memberId) {
        String sql = "SELECT * FROM orders WHERE member_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new OrderRowMapper(), memberId);
    }

    public OrderVO findByOrderNumber(String orderNumber) {
        String sql = "SELECT * FROM orders WHERE order_number = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new OrderRowMapper(), orderNumber);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int updateStatus(Long orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, status, orderId);
    }
}
