package com.fluxmall.mapper;


import com.fluxmall.model.Cart;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartRowMapper implements RowMapper<Cart> {

    @Override
    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cart cart = new Cart();
        cart.setId(rs.getLong("id"));
        cart.setMemberId(rs.getLong("member_id"));
        cart.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        cart.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return cart;
    }
}