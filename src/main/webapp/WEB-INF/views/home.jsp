<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FluxMall - 메인</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/layout/header.jsp" %>

    <main class="container">
        <h1 class="page-title">전체 상품</h1>

        <!-- 카테고리 필터 탭 -->
        <div class="category-tabs">
            <a href="/products" class="${empty category ? 'active' : ''}">전체</a>
            <c:forEach items="${categories}" var="cat">
                <a href="/products?category=${cat}" class="${category == cat ? 'active' : ''}">
                    ${cat.displayName}
                </a>
            </c:forEach>
        </div>

        <!-- 상품 그리드 -->
        <div class="product-grid">
            <c:forEach items="${products}" var="product">
                <div class="product-card">
                    <!-- 더미 이미지 (실제 이미지는 나중에) -->
                    <a href="/products/${product.id}">
                        <img src="https://via.placeholder.com/300x300?text=${product.name}" alt="${product.name}">
                    </a>

                    <div class="product-info">
                        <h3 class="product-name">
                            <a href="/products/${product.id}">${product.name}</a>
                        </h3>
                        <p class="product-price">
                            <strong>${product.price}원</strong>
                        </p>
                        <p class="product-stock">
                            <c:choose>
                                <c:when test="${product.stockQuantity > 0}">
                                    <span class="in-stock">재고: ${product.stockQuantity}개</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="out-of-stock">품절</span>
                                </c:otherwise>
                            </c:choose>
                        </p>

                        <div class="product-actions">
                            <a href="/products/${product.id}" class="btn-detail">상세보기</a>
                            <button onclick="addToCart(${product.id})" class="btn-cart"
                                    ${product.stockQuantity == 0 ? 'disabled' : ''}>
                                장바구니 담기
                            </button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- 페이징 -->
        <c:if test="${totalPages > 1}">
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}&category=${category}">‹ 이전</a>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="p">
                    <a href="?page=${p}&category=${category}" class="${p == currentPage ? 'active' : ''}">
                        ${p}
                    </a>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="?page=${currentPage + 1}&category=${category}">다음 ›</a>
                </c:if>
            </div>
        </c:if>
    </main>

    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>

    <script>
        function addToCart(productId) {
            fetch('/cart/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-CSRF-TOKEN': '${_csrf.token}'  // CSRF 토큰 추가
                },
                body: 'productId=' + productId + '&quantity=1'
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert(data.message);
                    // 헤더 장바구니 카운트 업데이트
                    document.getElementById('cartCount').textContent = data.cartItemCount;
                } else {
                    alert(data.message || '장바구니 담기 실패');
                    if (data.message.includes('로그인')) {
                        location.href = '/member/login';
                    }
                }
            })
            .catch(() => alert('오류 발생'));
        }
    </script>
</body>
</html>