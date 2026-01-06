<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>FluxMall - ${title}</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/layout/header.jsp" %>

    <main class="container">
        <c:if test="${not empty message}">
            <div class="alert success">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert error">${error}</div>
        </c:if>

        <!-- 여기서부터 각 페이지 내용 -->
        ${content}
        <!-- 여기까지 -->
    </main>

    <%@ include file="/WEB-INF/views/layout/footer.jsp" %>

    <script src="/js/common.js"></script>
</body>
</html>