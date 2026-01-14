package com.fluxmall.dao;

import com.fluxmall.domain.mapper.ProductRowMapper;
import com.fluxmall.domain.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductVO> findAll() {
        String sql = "SELECT * FROM products ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public ProductVO findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<ProductVO> findByStatus(String status) {
        String sql = "SELECT * FROM products WHERE status = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new ProductRowMapper(), status);
    }

    public List<ProductVO> searchByName(String keyword) {
        String sql = "SELECT * FROM products WHERE name LIKE ? OR description LIKE ? ORDER BY created_at DESC";
        String searchTerm = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new ProductRowMapper(), searchTerm, searchTerm);
    }

    public int updateStock(Long productId, int quantity) {
        String sql = "UPDATE products SET stock_quantity = stock_quantity - ?, status = CASE WHEN stock_quantity - ? <= 0 THEN 'SOLD_OUT' ELSE status END WHERE id = ?";
        return jdbcTemplate.update(sql, quantity, quantity, productId);
    }

    public int restoreStock(Long productId, int quantity) {
        String sql = "UPDATE products SET stock_quantity = stock_quantity + ?, status = 'ON_SALE' WHERE id = ?";
        return jdbcTemplate.update(sql, quantity, productId);
    }
}
