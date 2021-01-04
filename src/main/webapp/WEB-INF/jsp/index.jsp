<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Йога Index</title>
    <%@ include file="head.jsp" %>
</head>
<body>

<%@ include file="main_menu.jsp" %>
<ctg:welcome user="${sessionScope.authorizedUser}"/>

<div class="container-fluid text-center">
    <div class="row content">
        <%-- menu of the page--%>
        <div class="col-sm-2 sidenav text-left">
            <p><a href="registration.html">Registration</a></p>
            <p><a href="user/list.html">List users</a></p>
            <p><a href="#">Link</a></p>
        </div>
            <%--Content of the page --%>
        <div class="col-sm-8 text-justify">
            <p><img src="<c:url value="/img/yogaimg.jpg"/>" class="img-responsive center-block" width=400" alt="Поза лотоса с поднятыми вверх руками"/></p>

            <h2>Немного о йоге</h2>
            <p> Чем же отличается йога от физкультуры?
                Что нужно знать начинающим заниматься йогой в домашних условиях?

                Занятия йогой имеют свои особенности. Существует ряд нюансов, которые отличают йогу от других видов
                тренировок. Осознанность. Занятие йогой обычно начинается с пятиминутной медитации. Сядьте с прямой спиной как вам
                удобно, можно по турецки, в позу лотоса или на пятки. Закройте глаза. Настройтесь на практику, примите решение
                следующие полчаса посвятить ваше внимание своему развитию ни на что не отвлекаясь. Когда во время занятия йогой к
                вам придут мысли о проблемах и повседневных делах, спокойно пообещайте себе, что уделите им внимание через
                полчаса, но сейчас ваше внимание принадлежит тренировке. Для тренировки осознанности занятия йогой обычно
                сочетаются с медитацией. Читайте также подробную статью о том, что такое медитация, и как научиться медитировать с
                нуля.
                Работа с дыханием. Основа йоги — управление дыханием. Когда вы осознаёте своё дыхание и начинаете
                управлять им, обычная физическая тренировка становится йогой. Дыхание должно быть глубоким и спокойным. Основа
                йогического дыхания: полный вдох и полный выдох. Дыхательные циклы размеренные и равны между собой по времени.
                Начинающим заниматься йогой можно использовать метроном. Скачайте любое приложение с метрономом на свой смартфон,
                поставьте частоту 1 секунда. Подберите подходящую частоту вдохов и выдохов. Например, вдох на 4 счёта, выдох на 6.
                Выдох обычно дольше вдоха, либо выдох равен вдоху.
            </p>
            <hr>
            <h3>Test</h3>
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
                            <td class="success"><c:out value="${ status.count }"/></td>
                            <td class="info"><c:out value="${ complex.id }"/></td>
                            <td class="warning"><c:out value="${ complex.title }"/></td>
                            <td><c:out value="${ complex.trainerDeveloped.id }"/></td>
                            <td><c:out value="${ complex.rating }"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>
        <div class="col-sm-2 sidenav">
            <%@ include file="ads.jsp" %>

        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>
