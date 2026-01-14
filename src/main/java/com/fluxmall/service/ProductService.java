package com.fluxmall.service;

import com.fluxmall.dao.ProductDao;
import com.fluxmall.domain.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductVO> getAllProducts() {
        return productDao.findAll();
    }

    public List<ProductVO> getOnSaleProducts() {
        return productDao.findByStatus("ON_SALE");
    }

    public ProductVO getProductDetail(Long id) {
        return productDao.findById(id);
    }

    public List<ProductVO> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getOnSaleProducts();
        }
        return productDao.searchByName(keyword);
    }

    public boolean checkStockAvailable(Long productId, int quantity) {
        ProductVO product = productDao.findById(productId);
        return product != null && product.getStockQuantity() >= quantity && product.isOnSale();
    }
}
