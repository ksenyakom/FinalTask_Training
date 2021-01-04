<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="#" class="navbar-brand">Йога</a>
        </div>

        <div>
            <ul class="nav navbar-nav">
                <li class="nav-link"><a href="#">Главная</a></li>
                <li class="nav-link"><a href="#">Личный кабинет</a></li>
                <li class="nav-link"><a href="executed.html">Журнал выполнения тренировок</a></li>
                <li class="nav-link"><a href="#">О нас</a></li>
                <li class="nav-link"><a href="#">Контакты</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${empty sessionScope.authorizedUser}">
                        <li><a href="login.html"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="logout.html"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div>
    </div>
</nav>