package com.fluxmall.mapper;


import com.fluxmall.domain.CartItem;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemRowMapper implements RowMapper<CartItem> {

    @Override
    public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setId(rs.getLong("id"));
        cartItem.setCartId(rs.getLong("cart_id"));
        cartItem.setProductId(rs.getLong("product_id"));
        cartItem.setQuantity(rs.getInt("quantity"));
        cartItem.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        cartItem.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return cartItem;
    }
}