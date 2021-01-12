<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%--Локализация--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">

<head>
    <title><fmt:message key="title.index"/></title>
    <%@ include file="/WEB-INF/jsp/common/head.jsp" %>
</head>
<body>

<%@ include file="/WEB-INF/jsp/common/main_menu.jsp" %>

<div class="container-fluid text-center">
    <div class="row content">
        <%--side menu of the page--%>
            <c:import url="/WEB-INF/jsp/common/side_menu.jsp"/>

        <%--Content of the page --%>
        <div class="col-sm-8 text-justify">
            <br>
            <p><img src='<c:url value="/img/yogaimg.jpg"/>' class="img-responsive center-block" width=400"
                    alt="<fmt:message key="picture.caption.lotus_hands_up"/>"/></p>

            <h2>О йоге jsp</h2>
            <p> Чем же отличается йога от физкультуры?
                Что нужно знать начинающим заниматься йогой в домашних условиях?

                Занятия йогой имеют свои особенности. Существует ряд нюансов, которые отличают йогу от других видов
                тренировок. Осознанность. Занятие йогой обычно начинается с пятиминутной медитации. Сядьте с прямой
                спиной как вам
                удобно, можно по турецки, в позу лотоса или на пятки. Закройте глаза. Настройтесь на практику, примите
                решение
                следующие полчаса посвятить ваше внимание своему развитию ни на что не отвлекаясь. Когда во время
                занятия йогой к
                вам придут мысли о проблемах и повседневных делах, спокойно пообещайте себе, что уделите им внимание
                через
                полчаса, но сейчас ваше внимание принадлежит тренировке. Для тренировки осознанности занятия йогой
                обычно
                сочетаются с медитацией. Читайте также подробную статью о том, что такое медитация, и как научиться
                медитировать с
                нуля.
                Работа с дыханием. Основа йоги — управление дыханием. Когда вы осознаёте своё дыхание и начинаете
                управлять им, обычная физическая тренировка становится йогой. Дыхание должно быть глубоким и спокойным.
                Основа
                йогического дыхания: полный вдох и полный выдох. Дыхательные циклы размеренные и равны между собой по
                времени.
                Начинающим заниматься йогой можно использовать метроном. Скачайте любое приложение с метрономом на свой
                смартфон,
                поставьте частоту 1 секунда. Подберите подходящую частоту вдохов и выдохов. Например, вдох на 4 счёта,
                выдох на 6.
                Выдох обычно дольше вдоха, либо выдох равен вдоху.
            </p>
            <hr>
            <h3>Test</h3>

        </div>
        <div class="col-sm-2 sidenav">
            <c:import url="WEB-INF/jsp/common/ads.jsp"/>
        </div>
    </div>
</div>

<c:import url="WEB-INF/jsp/common/footer.jsp"/>
</body>
</html>
