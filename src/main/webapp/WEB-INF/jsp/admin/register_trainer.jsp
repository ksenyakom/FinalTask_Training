<%--
  Author: Ksiniya Oznobishina 
  Date: 07.02.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="${cookie.language.value}">
<%--проверить это--%>
<head>
    <title><fmt:message key="title.assigned_trainer_list"/></title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/validator_registration.js"/>"></script>

</head>
<body>
<%@ include file="../common/main_menu.jsp" %>
<div class="container-fluid">
    <div class="row content">
        <%-- menu of the page--%>
        <c:import url="../admin/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8">

            <h4 class="text-center"><fmt:message key="title.register_trainer"/></h4>


            <form action="<c:url value="/admin/register_trainer.html"/>" method="POST"
                  onsubmit="return validateRegistration(this)">
                <h2><fmt:message key="title.register_trainer"/></h2>
                <br>
                <div class="form-group">
                    <label for="login"><fmt:message key="label.login"/></label>
                    <input type="text" class="form-control" id="login"
                           name="login" value="${user.login}">
                    <p class="help-block"><fmt:message key="help_block.login"/></p>

                </div>
                <br>
                <div class="form-group">
                    <label for="password"><fmt:message key="label.password"/></label>
                    <input type="password" class="form-control" id="password"
                           name="password">
                    <p class="help-block"><fmt:message key="help_block.password"/></p>
                </div>
                <div class="form-group">
                    <label for="password2"><fmt:message key="label.password_again"/></label>
                    <input type="password" class="form-control" id="password2"
                           name="password2">
                </div>
                <br>
                <div class="form-group">
                    <label for="email"><fmt:message key="label.email"/></label>
                    <input type="email" class="form-control" id="email"
                           name="email" value="${user.email}">
                </div>

                <br>
                <p id="errorMessage" class="text-danger"></p>

                <%@ include file="../common/messages.jsp" %>

                <br>

                <button type="submit" class="btn btn-success"><fmt:message key="button.register_trainer"/></button>
            </form>


        </div>
        <div class="col-sm-2 sidenav">
            <%@ include file="../common/ads.jsp" %>

        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
