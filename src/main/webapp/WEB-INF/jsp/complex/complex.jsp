<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.01.2021
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <caption>
            <h4>Список тренировок:</h4>
        </caption>
        <tr class="active">
            <th scope="col">Number</th>
            <th scope="col">id</th>
            <th scope="col">Title</th>
            <th scope="col">trainerDeveloped</th>
            <th scope="col">rating</th>
        </tr>
        <c:forEach items="${lst}" var="complex" varStatus="status">
            <tr class="success">
                <td><c:out value="${ status.count }"/></td>
                <td><c:out value="${ complex.id }"/></td>
                <td><c:out value="${ complex.title }"/></td>
                <td><c:out value="${ complex.trainerDeveloped.id }"/></td>
                <td><c:out value="${ complex.rating }"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
