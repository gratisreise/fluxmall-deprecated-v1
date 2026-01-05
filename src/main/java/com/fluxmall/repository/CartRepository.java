package com.fluxmall.repository;


import com.fluxmall.mapper.CartRowMapper;
import com.fluxmall.model.Cart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 회원 장바구니 생성 또는 조회 (회원당 하나)
    public Cart findOrCreateByMemberId(Long memberId) {
        String sql = "SELECT * FROM carts WHERE member_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new CartRowMapper(), memberId);
        } catch (Exception e) {
            // 없으면 생성
            String insertSql = "INSERT INTO carts (member_id) VALUES (?)";
            jdbcTemplate.update(insertSql, memberId);
            return jdbcTemplate.queryForObject(sql, new CartRowMapper(), memberId);
        }
    }

    public Cart findByMemberId(Long memberId) {
        String sql = "SELECT * FROM carts WHERE member_id = ?";
        return jdbcTemplate.queryForObject(sql, new CartRowMapper(), memberId);
    }
}