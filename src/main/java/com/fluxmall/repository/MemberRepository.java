package com.fluxmall.repository;


import com.fluxmall.mapper.MemberRowMapper;
import com.fluxmall.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 회원 등록
    public void save(Member member) {
        String sql = "INSERT INTO members (username, password, nickname) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, member.getUsername(), member.getPassword(), member.getNickname());
    }

    // ID로 조회
    public Optional<Member> findById(Long id) {
        String sql = "SELECT * FROM members WHERE id = ?";
        try {
            Member member = jdbcTemplate.queryForObject(sql, new MemberRowMapper(), id);
            return Optional.of(member);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // username으로 조회 (로그인용)
    public Optional<Member> findByUsername(String username) {
        String sql = "SELECT * FROM members WHERE username = ?";
        try {
            Member member = jdbcTemplate.queryForObject(sql, new MemberRowMapper(), username);
            return Optional.of(member);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // 모든 회원 목록
    public List<Member> findAll() {
        String sql = "SELECT * FROM members ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new MemberRowMapper());
    }
}