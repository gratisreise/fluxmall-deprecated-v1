package com.fluxmall.repository;

import com.fluxmall.mapper.ProductRowMapper;
import com.fluxmall.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Product product) {
        String sql = "INSERT INTO products (member_id, name, description, price, stock_quantity, product_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getMemberId(), product.getName(), product.getDescription(),
                product.getPrice(), product.getStockQuantity(), product.getProductStatus());
    }

    public Product findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id);
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM products ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    // 회원이 등록한 상품 목록
    public List<Product> findByMemberId(Long memberId) {
        String sql = "SELECT * FROM products WHERE member_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new ProductRowMapper(), memberId);
    }
}