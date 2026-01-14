package com.fluxmall.service;

import com.fluxmall.dao.OrderDao;
import com.fluxmall.dao.ProductDao;
import com.fluxmall.domain.vo.MemberVO;
import com.fluxmall.domain.vo.OrderVO;
import com.fluxmall.domain.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderService {

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final MemberService memberService;

    @Autowired
    public OrderService(OrderDao orderDao, ProductDao productDao, MemberService memberService) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.memberService = memberService;
    }

    @Transactional
    public Long createOrder(Long productId, int quantity, HttpSession session) {
        MemberVO loginMember = memberService.getLoginMember(session);
        if (loginMember == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        ProductVO product = productDao.findById(productId);
        if (product == null || !product.isOnSale()) {
            throw new RuntimeException("판매 중인 상품이 아닙니다.");
        }

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("재고가 부족합니다.");
        }

        String orderNumber = generateOrderNumber();
        int totalPrice = product.getPrice() * quantity;

        OrderVO order = new OrderVO();
        order.setMemberId(loginMember.getId());
        order.setOrderNumber(orderNumber);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setTotalPrice(totalPrice);
        order.setStatus("PENDING");

        productDao.updateStock(productId, quantity);
        return orderDao.insert(order);
    }

    @Transactional
    public boolean processPayment(Long orderId, HttpSession session) {
        OrderVO order = orderDao.findById(orderId);
        if (order == null) {
            return false;
        }

        MemberVO loginMember = memberService.getLoginMember(session);
        if (loginMember == null || !loginMember.getId().equals(order.getMemberId())) {
            throw new RuntimeException("주문자가 아닙니다.");
        }

        order.setStatus("PAID");
        int updated = orderDao.updateStatus(orderId, "PAID");
        return updated > 0;
    }

    @Transactional
    public boolean cancelOrder(Long orderId, HttpSession session) {
        OrderVO order = orderDao.findById(orderId);
        if (order == null || !order.isPending()) {
            return false;
        }

        MemberVO loginMember = memberService.getLoginMember(session);
        if (loginMember == null || !loginMember.getId().equals(order.getMemberId())) {
            throw new RuntimeException("주문자가 아닙니다.");
        }

        productDao.restoreStock(order.getProductId(), order.getQuantity());
        order.setStatus("CANCELLED");
        return orderDao.updateStatus(orderId, "CANCELLED") > 0;
    }

    public OrderVO getOrderDetail(Long orderId, HttpSession session) {
        OrderVO order = orderDao.findById(orderId);
        if (order == null) {
            return null;
        }

        MemberVO loginMember = memberService.getLoginMember(session);
        if (loginMember != null && loginMember.getId().equals(order.getMemberId())) {
            ProductVO product = productDao.findById(order.getProductId());
            if (product != null) {
                order.setProductName(product.getName());
                order.setProductPrice(product.getPrice());
            }
        }

        return order;
    }

    public java.util.List<OrderVO> getMyOrders(HttpSession session) {
        MemberVO loginMember = memberService.getLoginMember(session);
        if (loginMember == null) {
            return java.util.Collections.emptyList();
        }
        return orderDao.findByMemberId(loginMember.getId());
    }

    private String generateOrderNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "ORD" + timestamp;
    }
}
