<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>list users</title>
    <%@ include file="../common/head.jsp" %>
</head>
<body>
<%@ include file="../common/main_menu.jsp" %>


<div class="container-fluid">
    <div class="row content">
        <%-- menu of the page--%>
        <c:import url="../admin/menu.jsp"/>
<%--        <div class="col-sm-2 sidenav text-left">--%>
<%--            <p><a href="<c:url value="/user/list.html"/>">Список пользователей</a></p>--%>
<%--            <p><a href="#">Поиск назначенного тренера/посетителя</a></p>--%>
<%--            <p><a href="#">Подписки посетителей</a></p>--%>
<%--            <p><a href="#">Link</a></p>--%>
<%--        </div>--%>
        <%--Content of the page --%>
        <div class="col-sm-8">

            <h4 class="text-center">Список пользователей</h4>
            <br>

            <br>
            <form action="list.html" method="GET">
                <div class="form-group">
                    <label class="control-label col-sm-offset-2 col-sm-2" for="role">Вывести:</label>
                    <div class="col-sm-6 col-md-4">
                        <select id="role" class="form-control" name="role">
                            <option value="Visitor">Посетители</option>
                            <option value="Trainer">Тренера</option>
                        </select>
                    </div>
                </div>
                <input type="submit" value="Показать"/></td>
            </form>

            <br>
            <form action="list.html" method="post">
            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                    <caption>

                    </caption>
                    <tr class="active">
                        <th scope="col">Number</th>
                        <th scope="col">Login</th>
                        <th scope="col">email</th>
                        <th scope="col">role</th>
                        <th scope="col">remove</th>
                    </tr>
                    <c:forEach items="${lst}" var="user" varStatus="status">
                        <tr class="success">
                            <td><c:out value="${ status.count }"/></td>
                            <td class="info"><c:out value="${ user.login }"/></td>
                            <td><c:out value="${ user.email}"/></td>
                            <td><c:out value="${ user.role }"/></td>
                            <td><input type="checkbox" name="remove" value="${user.id}"/></td>
                        </tr>
                    </c:forEach>
                </table>
                <button type="submit" class="btn btn-success text-right">Удалить</button>
            </div>
            </form>


        </div>
        <div class="col-sm-2 sidenav">
            <%@ include file="../common/ads.jsp" %>

        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
</body>
</html>
