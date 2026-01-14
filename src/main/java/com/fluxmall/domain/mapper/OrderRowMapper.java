package com.fluxmall.domain.mapper;

import com.fluxmall.domain.vo.OrderVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<OrderVO> {
    @Override
    public OrderVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderVO order = new OrderVO();
        order.setId(rs.getLong("id"));
        order.setMemberId(rs.getLong("member_id"));
        order.setOrderNumber(rs.getString("order_number"));
        order.setProductId(rs.getLong("product_id"));
        order.setQuantity(rs.getInt("quantity"));
        order.setTotalPrice(rs.getInt("total_price"));
        order.setStatus(rs.getString("status"));
        order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        order.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return order;
    }
}
