<%--
  User: User
  Date: 11.01.2021
  Time: 19:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="en">
<body>
<div class="col-sm-2 sidenav text-left">
    <p><a href='<c:url value="/user/list.html"/>'><fmt:message key="label.side_menu.admin.users"/></a></p>
    <p><a href='<c:url value="/assigned_trainer/list.html"/>'><fmt:message key="label.side_menu.admin.trainer-visitor_search"/></a></p>
    <p><a href='<c:url value="/subscription/list.html"/>'><fmt:message key="label.side_menu.admin.subscription"/></a></p>
    <p><a href='<c:url value="/exercise/list.html"/>'><fmt:message key="label.side_menu.exercise_list"/></a></p>
    <p><a href='<c:url value="/complex/my_complexes.html"/>'><fmt:message key="label.side_menu.trainings"/></a></p>
    <p><a href='<c:url value="/admin/register_trainer.html"/>'><fmt:message key="title.register_trainer"/></a></p>
    <p><a href='<c:url value="/report/subscription.html"/>'><fmt:message key="title.reports"/></a></p>
</div>
</body>
</html>
