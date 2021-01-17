<%--
  Author: Ksiniya Oznobishina 
  Date: 17.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<body>
<div class="col-sm-2 sidenav text-left">
<%--    <p><a href='<c:url value="/user/list.html"/>'><fmt:message key="label.side_menu.admin.users"/></a></p>--%>
    <p><a href='<c:url value="/visitor/assigned_trainings.html"/>'>Мои тренировки</a></p>
    <p><a href="#">Моя подписка</a></p>
    <p><a href='<c:url value="/visitor/edit.html"/>'>Редактировать личные данные</a></p>
    <p><a href='<c:url value="/user/edit.html"/>'>Изменить логин или пароль</a></p>
    <p><a href="#">Мой тренер</a></p>
</div>
</body>
</html>
