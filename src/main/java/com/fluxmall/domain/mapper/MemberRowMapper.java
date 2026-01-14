package com.fluxmall.domain.mapper;

import com.fluxmall.domain.vo.MemberVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<MemberVO> {
    @Override
    public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberVO member = new MemberVO();
        member.setId(rs.getLong("id"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("password"));
        member.setName(rs.getString("name"));
        member.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        member.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return member;
    }
}
