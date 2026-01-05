package com.fluxmall.mapper;


import com.fluxmall.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {

    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getLong("id"));
        orderItem.setOrderId(rs.getLong("order_id"));
        orderItem.setProductId(rs.getLong("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setPrice(rs.getInt("price"));
        orderItem.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        orderItem.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return orderItem;
    }
}