<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FluxMall - 주문서 작성</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/layout/header.jsp" %>

    <main class="container">
        <h1 class="page-title">주문서 작성</h1>

        <c:if test="${not empty error}">
            <div class="alert error">${error}</div>
        </c:if>

        <form action="/order/create" method="post" class="checkout-form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <!-- 주문 상품 목록 -->
            <section class="order-items-section">
                <h2>주문 상품 확인</h2>
                <table class="order-items-table">
                    <thead>
                        <tr>
                            <th>상품정보</th>
                            <th>가격</th>
                            <th>수량</th>
                            <th>소계</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${cartItems}" var="item">
                            <tr>
                                <td class="product-info">
                                    <img src="https://via.placeholder.com/80x80?text=${item.productName}"
                                         alt="${item.productName}">
                                    <div>
                                        <strong>${item.productName}</strong>
                                        <small>${item.productCategory}</small>
                                    </div>
                                </td>
                                <td><fmt:formatNumber value="${item.productPrice}" />원</td>
                                <td>${item.quantity}개</td>
                                <td><strong><fmt:formatNumber value="${item.productPrice * item.quantity}" />원</strong></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="total-price-summary">
                    <span>총 결제 예정 금액</span>
                    <strong class="final-price"><fmt:formatNumber value="${totalPrice}" />원</strong>
                </div>
            </section>

            <!-- 배송지 정보 -->
            <section class="shipping-section">
                <h2>배송지 정보</h2>
                <div class="form-group">
                    <label for="recipient">받는 사람 <span class="required">*</span></label>
                    <input type="text" id="recipient" name="shippingAddress" required
                           value="${member.nickname}" placeholder="받는 분 성함">
                    <small>예: 홍길동 또는 홍길동(회사)</small>
                </div>

                <div class="form-group full-address">
                    <label for="address">상세 주소 <span class="required">*</span></label>
                    <input type="text" id="address" name="shippingAddress" required
                           placeholder="ex) 서울시 강남구 테헤란로 123 길동빌딩 501호"
                           style="width:100%;">
                    <small>배송 시 정확한 주소를 입력해주세요. (우편번호는 생략 가능)</small>
                </div>
            </section>

            <!-- 최종 주문 버튼 -->
            <div class="checkout-actions">
                <a href="javascript:history.back()" class="btn-back">뒤로가기</a>
                <button type="submit" class="btn-order-final">
                    <fmt:formatNumber value="${totalPrice}" />원 결제하기
                </button>
            </div>
        </form>
    </main>

    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</body>
</html>