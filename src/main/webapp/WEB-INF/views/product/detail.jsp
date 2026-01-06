<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FluxMall - ${product.name}</title>
    <link rel="stylesheet" href="/css/style.css">
    <script>
        // 장바구니 담기 AJAX
        function addToCart() {
            const quantity = document.getElementById('quantity').value;
            const productId = ${product.id};

            fetch('/cart/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-CSRF-TOKEN': '${_csrf.token}'
                },
                body: 'productId=' + productId + '&quantity=' + quantity
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert(data.message);
                    document.getElementById('cartCount').textContent = data.cartItemCount;
                } else {
                    alert(data.message);
                    if (data.message.includes('로그인')) {
                        if (confirm('로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?')) {
                            location.href = '/member/login';
                        }
                    }
                }
            })
            .catch(() => alert('오류가 발생했습니다.'));
        }

        // 수량 증가/감소
        function changeQuantity(delta) {
            const input = document.getElementById('quantity');
            let value = parseInt(input.value);
            value = value + delta;
            if (value < 1) value = 1;
            if (value > ${product.stockQuantity}) {
                value = ${product.stockQuantity};
                alert('재고 수량을 초과할 수 없습니다.');
            }
            input.value = value;
        }
    </script>
</head>
<body>
    <%@ include file="/WEB-INF/views/layout/header.jsp" %>

    <main class="container">
        <div class="product-detail">
            <div class="product-images">
                <!-- 더미 이미지 -->
                <img src="https://via.placeholder.com/600x600?text=${product.name}"
                     alt="${product.name}" class="main-image">
            </div>

            <div class="product-summary">
                <h1 class="product-title">${product.name}</h1>

                <p class="product-category">
                    카테고리: <strong>${product.category.displayName}</strong>
                </p>

                <p class="product-price">
                    <span class="price-label">가격:</span>
                    <strong class="price-value">${product.price}원</strong>
                </p>

                <p class="product-stock">
                    재고:
                    <c:choose>
                        <c:when test="${product.stockQuantity > 0}">
                            <span class="in-stock">${product.stockQuantity}개</span>
                        </c:when>
                        <c:otherwise>
                            <span class="out-of-stock">품절</span>
                        </c:otherwise>
                    </c:choose>
                </p>

                <div class="quantity-selector">
                    <label>수량:</label>
                    <button type="button" onclick="changeQuantity(-1)">-</button>
                    <input type="number" id="quantity" value="1" min="1" max="${product.stockQuantity}" readonly>
                    <button type="button" onclick="changeQuantity(1)">+</button>
                </div>

                <div class="product-actions-detail">
                    <button onclick="addToCart()" class="btn-cart-large"
                            ${product.stockQuantity == 0 ? 'disabled' : ''}>
                        장바구니 담기
                    </button>
                    <a href="/order/direct?productId=${product.id}&quantity="
                       onclick="this.href += document.getElementById('quantity').value"
                       class="btn-buy-now"
                       ${product.stockQuantity == 0 ? 'style=pointer-events:none;opacity:0.6;' : ''}>
                        바로구매
                    </a>
                </div>
            </div>
        </div>

        <div class="product-description">
            <h2>상품 설명</h2>
            <div class="description-content">
                ${product.description}
            </div>
        </div>

        <div class="back-link">
            <a href="javascript:history.back()">← 목록으로 돌아가기</a>
        </div>
    </main>

    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</body>
</html>