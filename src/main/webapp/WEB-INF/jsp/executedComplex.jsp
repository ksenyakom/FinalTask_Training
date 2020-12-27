<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.12.2020
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Executed Complexes</title>
</head>
<body>
<table border="1">
    <caption>
        <h4>Выполненные тренировки:</h4>
    </caption>
    <tr>
        <th scope="col">Number</th>
        <th scope="col">id</th>
        <th scope="col">Title</th>
        <th scope="col">date executed</th>
    </tr>
    <c:forEach items="${lst}" var="assignedComplex" varStatus="status">
        <tr>
            <td><c:out value="${ status.count }"/></td>
            <td><c:out value="${ assignedComplex.id }"/></td>
            <td><c:out value="${ assignedComplex.complex.title }"/></td>
            <td><c:out value="${ assignedComplex.visitor.name }"/></td>
            <td><c:out value="${ assignedComplex.date }"/></td>
        </tr>
    </c:forEach>
</table>
<br>
</body>
</html>
