<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header class="header">
    <div class="container">
        <div class="logo">
            <a href="/">FluxMall</a>
        </div>

        <div class="search-bar">
            <form action="/products/search" method="get">
                <input type="text" name="keyword" placeholder="상품 검색" required>
                <button type="submit">검색</button>
            </form>
        </div>

        <div class="user-menu">
            <c:choose>
                <c:when test="${not empty sessionScope.loginMember}">
                    <span>${sessionScope.loginMember.nickname} 님</span>
                    <a href="/member/myPage">마이페이지</a>
                    <a href="/member/logout" onclick="this.closest('form').submit(); return false;">로그아웃</a>
                    <a href="/cart" class="cart-icon">
                        장바구니
                        <span id="cartCount" class="badge">
                                ${cartService.getCartItems(session).size()}
                        </span>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="/member/login">로그인</a>
                    <a href="/member/register">회원가입</a>
                    <a href="/cart" class="cart-icon">장바구니</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>

<!-- 로그아웃용 hidden form (Security 요구) -->
<form id="logoutForm" action="/member/logout" method="post" style="display:none;">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>