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
    <title>Журнал</title>
    <%@ include file="common/head.jsp" %>

</head>
<body>
<%@ include file="common/main_menu.jsp" %>

<div class="container-fluid text-center">
    <div class="row content">
        <%-- menu of the page--%>
        <div class="col-sm-2 sidenav text-left">

            <p><a href="<c:url value="/user/list.html"/>">List users</a></p>
            <p><a href="#">Link</a></p>
        </div>
        <%--Content of the page --%>
        <div class="col-sm-8 text-justify">
            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                        <h4>Журнал тренировок:</h4>
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

            <hr>
            <h3>Test</h3>

        </div>
        <div class="col-sm-2 sidenav">
            <%@ include file="common/ads.jsp" %>
        </div>
    </div>
</div>

<%@ include file="common/footer.jsp" %>
<br>
</body>
</html>
