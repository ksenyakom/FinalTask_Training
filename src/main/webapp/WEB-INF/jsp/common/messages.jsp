<%--
  Author: Ksiniya Oznobishina 
  Date: 30.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Messages</title>
    <%@ include file="head.jsp" %>
</head>
<body>
<c:if test="${not empty warningMap}">
    <c:forEach var="warning" items="${warningMap}">
        <p class="bg-warning">
            <fmt:message key="${warning.key }"/> : <fmt:message key="${ warning.value }"/></p>
    </c:forEach>
</c:if>
<c:if test="${ not empty warningMessage }">
    <p class="bg-warning"><fmt:message key="${ warningMessage }"/></p></c:if>
<c:if test="${not empty successMessage }">
    <p class="bg-success"><fmt:message key="${ successMessage }"/></p></c:if>
<c:if test="${not empty errorMessage }">
    <p class="bg-danger"><fmt:message key="${ errorMessage }"/></p></c:if>

</body>
</html>

