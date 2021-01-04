<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 30.12.2020
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Редактирование</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <style>
        body {
            padding: 30px;
        }
    </style>
</head>
<body>
<h2>Редактирование</h2>
<c:url value="registration.html" var="url"/>
<form action="${url}" method="POST">
    <div class="container">
        <div class="form-group">
            <label for="lgn">Логин</label>
            <input type="text" class="form-control" id="lgn" placeholder="Введите логин"
                   name="login"
                   required
                   value=${param.login}>
        </div>
        <br>
        <div class="form-group">
            <label for="newPassword">Новый пароль</label>
            <input type="password" class="form-control" id="newPassword" placeholder="Введите пароль"
                   name="password">
            <p class="help-block">Подсказка</p>
        </div>
        <br>
        <div class="form-group">
            <label for="password2">Пароль повторно</label>
            <input type="password" class="form-control" id="password2" placeholder="Введите пароль повторно"
                   name="password2">
        </div>

        <div class="form-group">
            <label for="password">Старый пароль</label>
            <input type="password" class="form-control" id="password" placeholder="Введите пароль"
                   name="password">
            <p class="help-block">Подсказка</p>
        </div>

        <br>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" placeholder="Введите email"
                   name="email"
                   required
                   value=${param.email}>

        </div>

        <br>

        <button type="submit" class="btn btn-success">Вход</button>
    </div>
</form>
</body>
</html>
