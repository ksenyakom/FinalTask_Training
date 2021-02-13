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
    <p><a href='<c:url value="/visitor/assigned_trainings.html"/>'> <fmt:message key="label.my_trainings"/></a></p>
    <p><a href='<c:url value="/visitor/subscription.html"/>'><fmt:message key="label.my_subscription"/></a></p>
    <p><a href='<c:url value="/person/edit.html"/>'><fmt:message key="label.edit_person"/></a></p>
    <p><a href='<c:url value="/user/edit_login.html"/>'><fmt:message key="label.edit_login"/></a></p>
    <p><a href="#"><fmt:message key="label.edit_password"/></a></p>
    <p><a href="#"><fmt:message key="label.my_trainer"/></a></p>
</div>
</body>
</html>
