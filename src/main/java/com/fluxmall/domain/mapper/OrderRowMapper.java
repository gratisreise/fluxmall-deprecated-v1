package com.fluxmall.domain.mapper;


import com.fluxmall.domain.Order;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setMemberId(rs.getLong("member_id"));
        order.setOrderNumber(rs.getString("order_number"));
        order.setTotalPrice(rs.getInt("total_price"));
        order.setOrderStatus(rs.getString("order_status"));
        order.setShippingAddress(rs.getString("shipping_address"));
        order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        order.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return order;
    }
}