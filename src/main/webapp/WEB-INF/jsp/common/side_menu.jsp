<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 11.01.2021
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>

<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="col-sm-2 sidenav text-left">
    <p><a href='<c:url value="/user/list.html"/>'>Личный кабинет админа</a></p>
    <p><a href='<c:url value="/visitor/list.html"/>'>Личный кабинет тренера</a></p>
    <p><a href='<c:url value="/visitor/assigned_trainings.html"/>'>Личный кабинет посетителя</a></p>
    <p><a href='<c:url value="/complex/list.html"/>'><fmt:message key="label.side_menu.trainings"/></a></p>
    <p><a href='<c:url value="/journal.html"/>'><fmt:message key="label.main_menu.journal"/></a></p>
</div>
</body>
</html>
