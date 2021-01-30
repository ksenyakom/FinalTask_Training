<%--
  User: Kseniya Oznobishina
  Date: 29.12.2020
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Регистрация</title>
    <%@ include file="common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/validator_registration.js"/>"></script>
</head>

<body>
<%@ include file="common/main_menu.jsp" %>



<form action="<c:url value="registration.html"/>" method="POST" onsubmit="return validateRegistration(this)">
    <div class="container">
        <h2>Регистрация</h2>
        <br>
        <div class="form-group">
            <label for="login">Логин</label>
            <input type="text" class="form-control" id="login" placeholder="Введите логин"
                   name="login"  value="${param.login}" >
            <p class="help-block">Логин может включать прописные и строчные буквы, цифры, символы _,-</p>

        </div>
        <br>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" class="form-control" id="password" placeholder="Введите пароль"
                   name="password" >
            <p class="help-block">Пароль должен состоять из не менее 5 символов</p>
        </div>
        <div class="form-group">
            <label for="password2">Пароль повторно</label>
            <input type="password" class="form-control" id="password2" placeholder="Введите пароль повторно"
                   name="password2" >
        </div>
        <br>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" placeholder="Введите email"
                   name="email" value="${param.email}" >
        </div>
        <br>
        <div class="checkbox">
            <label for="password">
                <input type="checkbox" name="remember"> Запомнить меня
            </label>
        </div>
        <br>
        <p id="errorMessage" class="text-danger"></p>

        <c:if test="${not empty warningMessage}">
            <p class="text-danger"><fmt:message key="${warningMessage}"/></p>
        </c:if>

        <br>

        <button type="submit" class="btn btn-success">Регистрация</button>
    </div>
</form>
</body>
</html>
