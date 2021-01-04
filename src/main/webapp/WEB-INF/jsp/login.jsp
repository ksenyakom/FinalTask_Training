<%--
  Created by IntelliJ IDEA.
  User: Kseniya Oznobishina
  Date: 20.12.2020

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Авторизация</title>
    <%@ include file="head.jsp" %>
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>--%>
<%--    <script type="text/javascript" src="<c:url value="/js/jquery-3.5.1.min.js"/>"></script>--%>
    <script type="text/javascript" src="<c:url value="/js/test.js"/>"></script>

</head>

<body>
<%@ include file="main_menu.jsp" %>
<%-- --%>
<form action="<c:url value="/login.html"/>" method="POST"  onsubmit="return validateLogin(this)">
    <div class="container">
        <h2>Вход в систему</h2>
        <br>
        <div class="form-group">
            <label for="login">Логин</label>
            <input type="text" class="form-control" id="login" placeholder="Введите логин"
                   name="login" value="${param.login}" required>
            <p class="help-block">Логин может включать прописные и строчные буквы, цифры, символы _ и -</p>
        </div>
        <br>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" class="form-control" id="password" placeholder="Введите пароль"
                   name="password" >
            <p class="help-block">Пароль должен состоять из не менее 5 символов</p>
        </div>

        <div class="checkbox">
            <label for="password">
                <input type="checkbox" name="remember"> Запомнить меня
            </label>
        </div>
        <br>
        <p id="errorMessage" class="text-danger"></p>
        <br>
        <button type="submit" class="btn btn-success">Вход</button>
    </div>
</form>


</body>
</html>
