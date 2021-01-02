<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 29.12.2020
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>list users</title>
    <meta charset="utf-8">
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
        /* Remove the navbar's default margin-bottom and rounded borders */
        .navbar {
            margin-bottom: 0;
            border-radius: 0;
        }

        /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
        .row.content {height: 450px}

        /* Set gray background color and 100% height */
        .sidenav {
            padding-top: 20px;
            background-color: #f1f1f1;
            height: 100%;
        }

        /* Set black background color, white text and some padding */
        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }

        /* On small screens, set height to 'auto' for sidenav and grid */
        @media screen and (max-width: 767px) {
            .sidenav {
                height: auto;
                padding: 15px;
            }
            .row.content {height:auto;}
        }
    </style>
</head>
<body>
<h4>Список пользователей:</h4>
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
<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <caption>

        </caption>
        <tr class="active">
            <th scope="col">Number</th>
            <th scope="col">Login</th>
            <th scope="col">email</th>
            <th scope="col">role</th>
        </tr>
        <c:forEach items="${lst}" var="user" varStatus="status">
            <tr class="success">
                <td><c:out value="${ status.count }"/></td>
                <td class="info"><c:out value="${ user.login }"/></td>
                <td><c:out value="${ user.email}"/></td>
                <td><c:out value="${ user.role }"/></td>

            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
