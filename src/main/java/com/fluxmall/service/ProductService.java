package com.fluxmall.service;


import com.fluxmall.dao.ProductDao;
import com.fluxmall.domain.enums.ProductCategory;
import com.fluxmall.domain.enums.ProductStatus;
import com.fluxmall.domain.vo.MemberVO;
import com.fluxmall.domain.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    /**
     * 상품 등록
     * @param product 등록할 상품 정보 (name, description, category, price, stockQuantity)
     * @param session 로그인 세션
     * @return 등록 성공 여부
     */
    @Transactional
    public boolean registerProduct(ProductVO product, HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            return false;  // 로그인 안 하면 등록 불가
        }

        // 판매자 ID 자동 주입
        product.setMemberId(loginMember.getId());
        // 기본 상태: 판매중
        product.setStatus(ProductStatus.ON_SALE);

        return productDao.insertProduct(product) > 0;
    }

    /**
     * 상품 상세 조회
     */
    public ProductVO getProductById(Long id) {
        ProductVO product = productDao.findById(id);
        if (product != null && product.getStatus() == ProductStatus.ON_SALE) {
            return product;
        }
        return null;  // 판매중이 아니면 null 반환 (또는 예외 처리 나중에)
    }

    /**
     * 상품 목록 조회 - 페이징 + 카테고리 필터
     * @param page 현재 페이지 (1부터 시작)
     * @param size 한 페이지당 상품 수
     * @param category 필터링할 카테고리 (null이면 전체)
     * @return 상품 리스트
     */
    public List<ProductVO> getProductList(int page, int size, ProductCategory category) {
        int offset = (page - 1) * size;
        return productDao.findAll(offset, size, category);
    }

    /**
     * 상품 키워드 검색 (상품명 OR 설명)
     * @param keyword 검색어
     * @param page 페이지
     * @param size 페이지 크기
     * @return 검색 결과 리스트
     */
    public List<ProductVO> searchProducts(String keyword, int page, int size) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getProductList(page, size, null);  // 키워드 없으면 전체 목록
        }

        // ProductDao에 검색 메서드 추가 필요 → 아래에 추가 제안
        int offset = (page - 1) * size;
        return productDao.searchByKeyword(keyword.trim(), offset, size);
    }

    /**
     * 전체 상품 수 조회 (페이징용)
     */
    public int getTotalProductCount(ProductCategory category) {
        // 필요시 ProductDao에 count 메서드 추가
        // 지금은 간단히 전체 목록 크기로 대체 (실무에선 별도 COUNT 쿼리)
        return productDao.findAll(0, Integer.MAX_VALUE, category).size();
    }

    /**
     * 재고 차감 (주문 확정 시 OrderService에서 호출)
     * @return 차감 성공 여부 (재고 부족 시 false)
     */
    @Transactional
    public boolean decreaseStock(Long productId, int quantity) {
        if (quantity <= 0) return false;

        int updatedRows = productDao.updateStock(productId, quantity);
        if (updatedRows > 0) {
            // 재고가 0이 되면 자동으로 품절 처리 (옵션)
            ProductVO product = productDao.findById(productId);
            if (product != null && product.getStockQuantity() <= 0) {
                product.setStatus(ProductStatus.SOLD_OUT);
                // 별도 update 쿼리 필요시 추가
            }
            return true;
        }
        return false;  // 재고 부족
    }
}