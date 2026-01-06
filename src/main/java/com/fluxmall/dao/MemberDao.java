package com.fluxmall.dao;

import com.fluxmall.domain.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    // username으로 회원 조회 (로그인, 중복 체크용)
    public MemberVO findByUsername(String username) {
        String sql = "SELECT * FROM members WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new MemberRowMapper(), username);
        } catch (Exception e) {
            return null; // 조회 결과 없으면 null
        }
    }

    // 회원가입 (INSERT)
    public int insertMember(MemberVO member) {
        String sql = "INSERT INTO members (username, password, nickname) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, 
                member.getUsername(), 
                member.getPassword(), 
                member.getNickname());
    }

    // 회원 정보 수정 (비밀번호 변경, 닉네임 변경 등)
    public int updateMember(MemberVO member) {
        String sql = "UPDATE members SET password = ?, nickname = ? WHERE id = ?";
        return jdbcTemplate.update(sql, 
                member.getPassword(), 
                member.getNickname(), 
                member.getId());
    }

    // RowMapper
    private static class MemberRowMapper implements RowMapper<MemberVO> {
        @Override
        public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return MemberVO.builder()
                    .id(rs.getLong("id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .nickname(rs.getString("nickname"))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                    .build();
        }
    }
}