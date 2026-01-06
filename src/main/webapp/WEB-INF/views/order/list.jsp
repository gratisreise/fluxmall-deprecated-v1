<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FluxMall - 주문 내역</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/layout/header.jsp" %>

    <main class="container">
        <h1 class="page-title">주문 내역</h1>

        <c:choose>
            <c:when test="${empty orders}">
                <div class="empty-order">
                    <p>아직 주문 내역이 없습니다.</p>
                    <a href="/products" class="btn-primary">쇼핑하러 가기</a>
                </div>
            </c:when>
            <c:otherwise>
                <table class="order-list-table">
                    <thead>
                        <tr>
                            <th>주문번호</th>
                            <th>주문일시</th>
                            <th>총 결제금액</th>
                            <th>주문상태</th>
                            <th>상세보기</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orders}" var="order">
                            <tr>
                                <td class="order-number">
                                    <strong>${order.orderNumber}</strong>
                                </td>
                                <td>
                                    <fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
                                </td>
                                <td class="price">
                                    <strong><fmt:formatNumber value="${order.totalPrice}" />원</strong>
                                </td>
                                <td class="status">
                                    <span class="status-badge status-${order.status.name().toLowerCase()}">
                                        ${order.status.displayName}
                                    </span>
                                </td>
                                <td>
                                    <a href="/order/${order.id}" class="btn-detail-small">상세보기</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- 페이징 -->
                <c:if test="${totalPages > 1}">
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?page=${currentPage - 1}">‹ 이전</a>
                        </c:if>

                        <c:forEach begin="1" end="${totalPages}" var="p">
                            <a href="?page=${p}" class="${p == currentPage ? 'active' : ''}">${p}</a>
                        </c:forEach>

                        <c:if test="${currentPage < totalPages}">
                            <a href="?page=${currentPage + 1}">다음 ›</a>
                        </c:if>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </main>

    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</body>
</html>