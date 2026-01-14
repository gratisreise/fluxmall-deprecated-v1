package com.fluxmall.dao;

import com.fluxmall.domain.mapper.MemberRowMapper;
import com.fluxmall.domain.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MemberVO findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new MemberRowMapper(), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public MemberVO findById(Long id) {
        String sql = "SELECT * FROM members WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new MemberRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int insert(MemberVO member) {
        String sql = "INSERT INTO members (email, password, name) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, member.getEmail(), member.getPassword(), member.getName());
    }

    public int update(MemberVO member) {
        String sql = "UPDATE members SET password = ?, name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, member.getPassword(), member.getName(), member.getId());
    }
}
