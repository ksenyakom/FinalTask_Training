<%--
  Author: Ksiniya Oznobishina 
  Date: 17.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.edit_login"/></title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/validator_login.js"/>"></script>
    <%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>--%>
    <%--    <script type="text/javascript" src="<c:url value="/js/jquery-3.5.1.min.js"/>"></script>--%>
</head>

<body>
<%@ include file="../common/main_menu.jsp" %>
<%-- --%>
<form action="<c:url value="save_changes_login.html"/>" method="POST"  onsubmit="return validateLogin(this)">
    <div class="container">

        <h2><fmt:message key="label.system_enter"/></h2>
        <br>
        <div class="form-group">
            <label for="login"><fmt:message key="label.login"/></label>
            <input type="text" class="form-control" id="login"
                   name="login" value="${sessionScope.authorizedUser.login}" required>
            <p class="help-block"><fmt:message key="help_block.login"/></p>
        </div>
        <br>
        <div class="form-group">
            <label for="password"><fmt:message key="label.old_password"/></label>
            <input type="password" class="form-control" id="password"
                   name="password" >
            <p class="help-block"><fmt:message key="help_block.password"/></p>
        </div>
        <br>
        <p id="errorMessage" class="text-danger"></p>
        <br>
        <p class="text-danger">${requestScope.warning_message}</p>

        <button type="submit" class="btn btn-success"><fmt:message key="button.save_changes"/></button>
    </div>
</form>


</body>
</html>
