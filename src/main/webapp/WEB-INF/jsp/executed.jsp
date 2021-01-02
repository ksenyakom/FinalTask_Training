<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.12.2020
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Выполненные</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <style>
        body {
            padding: 30px;
        }
    </style>
</head>
<body>

<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <caption>
            <h4>Список тренировок:</h4>
        </caption>
        <tr class="active">
        <th scope="col">№</th>
        <th scope="col">id</th>
        <th scope="col">Название</th>
        <th scope="col">Имя пользователя</th>
        <th scope="col">Дата выполнения</th>
    </tr>
    <c:forEach items="${lst}" var="assignedComplex" varStatus="status">
        <tr class="success">
            <td><c:out value="${ status.count }"/></td>
            <td><c:out value="${ assignedComplex.id }"/></td>
            <td><c:out value="${ assignedComplex.complex.title }"/></td>
            <td><c:out value="${ assignedComplex.visitor.name }"/></td>
            <td><c:out value="${ assignedComplex.dateExecuted }"/></td>
        </tr>
    </c:forEach>
</table>
</div>
<br>
</body>
</html>
