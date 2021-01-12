<%--
  User: User
  Date: 11.01.2021
  Time: 19:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
</head>
<body>
<div class="col-sm-2 sidenav text-left">
    <p><a href='<c:url value="/user/list.html"/>'>Список пользователей</a></p>
    <p><a href="#">Поиск назначенного тренера/посетителя</a></p>
    <p><a href="#">Подписки посетителей</a></p>
    <p><a href="#">Link</a></p>
</div>
</body>
</html>
