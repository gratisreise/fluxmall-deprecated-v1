<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FluxMall - 주문 완료</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/layout/header.jsp" %>

    <main class="container">
        <div class="order-success-container">
            <div class="success-icon">
                ✅
            </div>

            <h1 class="success-title">주문이 완료되었습니다!</h1>

            <p class="success-message">
                감사합니다. 고객님의 주문이 성공적으로 접수되었습니다.<br>
                빠른 배송으로 찾아뵙겠습니다.
            </p>

            <div class="order-summary-box">
                <h2>주문 정보</h2>
                <table class="order-summary-table">
                    <tr>
                        <th>주문번호</th>
                        <td><strong>${order.orderNumber}</strong></td>
                    </tr>
                    <tr>
                        <th>주문일시</th>
                        <td>${order.createdAt}</td>
                    </tr>
                    <tr>
                        <th>결제금액</th>
                        <td><strong class="final-price"><fmt:formatNumber value="${order.totalPrice}" />원</strong></td>
                    </tr>
                    <tr>
                        <th>주문상태</th>
                        <td><span class="status-pending">${order.status.displayName}</span></td>
                    </tr>
                </table>
            </div>

            <div class="success-actions">
                <a href="/order/list" class="btn-primary">주문 내역 보기</a>
                <a href="/products" class="btn-secondary">쇼핑 계속하기</a>
            </div>

            <div class="success-notice">
                <p>※ 주문 내역은 <strong>마이페이지 > 주문 내역</strong>에서도 확인 가능합니다.</p>
                <p>※ 배송 관련 문의는 고객센터로 연락 부탁드립니다.</p>
            </div>
        </div>
    </main>

    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</body>
</html>