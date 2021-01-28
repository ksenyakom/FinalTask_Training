<%--
  Author: Ksiniya Oznobishina 
  Date: 15.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.error"/></title>
</head>
<body>
<p><img src='<c:url value="/img/yogaimg.jpg"/>' class="img-responsive center-block" width=400
        alt="<fmt:message key="picture.caption.lotus_hands_up"/>"/></p>
<c:choose>

    <c:when test="${not empty errorMessage}">
        <H2>${errorMessage}</H2>
    </c:when>
    <c:when test="${not empty pageContext.errorData.requestURI}">
        <H2>Запрошенная страница ${pageContext.errorData.requestURI} не найдена на сервере</H2>
    </c:when>
    <c:otherwise>Непредвиденная ошибка приложения</c:otherwise>
</c:choose>
<a href='<c:url value="/index.jsp"/>'><fmt:message key="label.main_menu.main"/></a>

</body>
</html>
