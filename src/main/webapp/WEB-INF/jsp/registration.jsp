<%--
  User: Kseniya Oznobishina
  Date: 29.12.2020
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="title.registration"/></title>
    <%@ include file="common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/validator_registration.js"/>"></script>
</head>

<body>
<%@ include file="common/main_menu.jsp" %>

<form action="<c:url value="registration.html"/>" method="POST" onsubmit="return validateRegistration(this)">
    <div class="container">
        <h2><fmt:message key="title.registration"/></h2>
        <br>
        <div class="form-group">
            <label for="login"><fmt:message key="label.login"/></label>
            <input type="text" class="form-control" id="login" placeholder="Введите логин"
                   name="login"  value="${user.login}" >
            <p class="help-block">Логин может включать прописные и строчные буквы, цифры, символы _,-</p>

        </div>
        <br>
        <div class="form-group">
            <label for="password"><fmt:message key="label.password"/></label>
            <input type="password" class="form-control" id="password" placeholder="Введите пароль"
                   name="password" >
            <p class="help-block">Пароль должен состоять из не менее 5 символов</p>
        </div>
        <div class="form-group">
            <label for="password2"><fmt:message key="label.password_again"/></label>
            <input type="password" class="form-control" id="password2" name="password2" >
        </div>
        <br>
        <div class="form-group">
            <label for="email"><fmt:message key="label.email"/></label>
            <input type="email" class="form-control" id="email"
                   name="email" value="${user.email}" >
        </div>
        <br>
        <div class="checkbox">
            <label for="password">
                <input type="checkbox" name="remember"> <fmt:message key="label.remember_me"/>
            </label>
        </div>
        <br>
        <p id="errorMessage" class="text-danger"></p>

        <%@ include file="common/messages.jsp" %>

        <br>

        <button type="submit" class="btn btn-success"><fmt:message key="button.register"/></button>
    </div>
</form>
</body>
</html>
