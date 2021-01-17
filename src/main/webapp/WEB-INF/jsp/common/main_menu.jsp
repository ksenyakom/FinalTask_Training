<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Menu</title>
    <%@ include file="head.jsp" %>
</head>
<body>
<%--выбор языка--%>
<div class="text-right">
    <a href='<c:url value="/change_language.html?language=ru_RU&page=${requestScope['javax.servlet.forward.request_uri']}"/>'>рус</a>
    <a href='<c:url value="/change_language.html?language=en_US&page=${requestScope['javax.servlet.forward.request_uri']}"/>'>eng</a>
</div>

<nav class="navbar navbar-default">
    <div class="container-fluid text-left">
        <div class="navbar-header">
            <a href="#" class="navbar-brand"><fmt:message key="label.main_menu.title"/></a>
        </div>

        <div>
            <ul class="nav navbar-nav">
                <li class="nav-link"><a href="#"><fmt:message key="label.main_menu.about_us"/></a></li>
                <li class="nav-link"><a href='<c:url value="/index.jsp"/>'><fmt:message key="label.main_menu.main"/></a></li>
                <li class="nav-link"><a href="#"><fmt:message key="label.main_menu.room"/></a></li>
                <li class="nav-link"><a href="#"><fmt:message key="label.main_menu.contacts"/></a></li>
            </ul>


            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${empty sessionScope.authorizedUser}">
                        <li><a href='<c:url value="/login.html"/>'><span class="glyphicon glyphicon-log-in"></span>
                            <fmt:message key="label.main_menu.login"/></a></li>
                        <li><a href='<c:url value="/registration.html"/>'><span
                                class="glyphicon glyphicon-registration-mark"></span><fmt:message key="label.main_menu.registration"/></a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href='<c:url value="/logout.html"/>'><span class="glyphicon glyphicon-log-out"></span>
                            Logout</a></li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div>
    </div>
</nav>

</body>
</html>

