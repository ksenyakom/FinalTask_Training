<%--
  User: Kseniya Oznobishina
  Date: 20.12.2020
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="title.login"/></title>
    <%@ include file="common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/validator_login.js"/>"></script>

</head>

<body>
<%@ include file="common/main_menu.jsp" %>
<%-- --%>
<form action="<c:url value="/login.html"/>" method="POST"  onsubmit="return validateLogin(this)">
    <div class="container">
        <h2><fmt:message key="label.system_enter"/></h2>
        <br>
        <div class="form-group">
            <label for="login"><fmt:message key="label.login"/>*</label>
            <input type="text" class="form-control" id="login"
                   name="login" value="${param.login}" required>
            <p class="help-block"><fmt:message key="help_block.login"/></p>
        </div>
        <br>
        <div class="form-group">
            <label for="password"><fmt:message key="label.password"/>*</label>
            <input type="password" class="form-control" id="password"
                   name="password" >
            <p class="help-block"><fmt:message key="help_block.password"/></p>
        </div>
        <div class="checkbox">
            <label for="remember">
                <input type="checkbox" name="remember" id="remember"> <fmt:message key="checkbox.remember_me"/>
            </label>
        </div>
        <br>
        <p id="errorMessage" class="text-danger"></p> <!--validator error message-->
        <%@ include file="common/messages.jsp" %>

        <br>
        <button type="submit" class="btn btn-success"><fmt:message key="button.sing_in"/></button>
    </div>
</form>


</body>
</html>
