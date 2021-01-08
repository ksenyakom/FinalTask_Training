<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Menu</title>
    <%@ include file="head.jsp" %>
</head>
<body>
<%--выбор языка--%>
<div class="text-right">
    <a href='<c:url value="/change_language.html?language=ru&country=RU"/>'>ru</a>
    <a href='<c:url value="/change_language.html?language=en&country=US"/>'>en</a>
</div>

<nav class="navbar navbar-default">
    <div class="container-fluid text-left">
        <div class="navbar-header">
            <a href="#" class="navbar-brand">Йога</a>
        </div>

        <div>
            <ul class="nav navbar-nav">
                <li class="nav-link"><a href='<c:url value="/index.html"/>'>Главная</a></li>
                <li class="nav-link"><a href="#">Личный кабинет</a></li>
                <li class="nav-link"><a href='<c:url value="/journal.html"/>'>Журнал</a></li>
                <li class="nav-link"><a href="#">О нас</a></li>
                <li class="nav-link"><a href="#">Контакты</a></li>
            </ul>


            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${empty sessionScope.authorizedUser}">
                        <li><a href='<c:url value="/login.html"/>'><span class="glyphicon glyphicon-log-in"></span>
                            Login</a></li>
                        <li><a href='<c:url value="/registration.html"/>'><span
                                class="glyphicon glyphicon-registration-mark"></span>Registration</a></li>
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