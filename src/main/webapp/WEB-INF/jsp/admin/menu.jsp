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
<head>
    <title>Title</title>
</head>
<body>
<div class="col-sm-2 sidenav text-left">
    <p><a href='<c:url value="/user/list.html"/>'><fmt:message key="label.side_menu.admin.users"/></a></p>
    <p><a href="#"><fmt:message key="label.side_menu.admin.trainer-visitor_search"/></a></p>
    <p><a href='<c:url value="/subscription/list.html"/>'><fmt:message key="label.side_menu.admin.subscription"/></a></p>
    <p><a href="#">Link</a></p>
</div>
</body>
</html>
