<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FluxMall - 마이페이지</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/layout/header.jsp" %>

    <main class="container">
        <h1 class="page-title">마이페이지</h1>

        <div class="mypage-container">
            <c:if test="${not empty message}">
                <div class="alert success">${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert error">${error}</div>
            </c:if>

            <!-- 회원 정보 표시 -->
            <section class="member-info-section">
                <h2>회원 정보</h2>
                <table class="member-info-table">
                    <tr>
                        <th>아이디</th>
                        <td>${member.username}</td>
                    </tr>
                    <tr>
                        <th>닉네임</th>
                        <td>${member.nickname}</td>
                    </tr>
                    <tr>
                        <th>가입일</th>
                        <td><fmt:formatDate value="${member.createdAt}" pattern="yyyy년 MM월 dd일"/></td>
                    </tr>
                </table>

                <div class="mypage-links">
                    <a href="/order/list" class="btn-secondary">주문 내역 보기</a>
                </div>
            </section>

            <!-- 정보 수정 폼 -->
            <section class="update-info-section">
                <h2>정보 수정</h2>
                <form action="/member/update" method="post" class="update-form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="id" value="${member.id}"/>

                    <div class="form-group">
                        <label for="nickname">닉네임</label>
                        <input type="text" id="nickname" name="nickname" value="${member.nickname}"
                               required maxlength="15" placeholder="변경할 닉네임">
                    </div>

                    <div class="form-group">
                        <label for="password">새 비밀번호 (변경 시 입력)</label>
                        <input type="password" id="password" name="password" minlength="6"
                               placeholder="6자 이상, 변경하지 않으면 비워두세요">
                        <small>비워두면 기존 비밀번호 유지</small>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn-update">정보 수정</button>
                    </div>
                </form>
            </section>
        </div>
    </main>

    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>
</body>
</html>