<%--
  Author: Kseniya Oznobishina
  Date: 30.12.2020
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.edit_person"/></title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/validator_login.js"/>"></script>
    <%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>--%>
    <%--    <script type="text/javascript" src="<c:url value="/js/jquery-3.5.1.min.js"/>"></script>--%>
</head>

<body>
<%@ include file="../common/main_menu.jsp" %>
<%-- --%>
<form action="<c:url value="save_changes.html"/>" method="POST">
    <div class="container">
        <h2><fmt:message key="label.system_enter"/></h2>
        <br>
        <div class="form-group">
            <label for="name"><fmt:message key="label.name"/></label>
            <input type="text" class="form-control" id="name"
                   name="name" value="${person.name}"/>
        </div>
        <br>
        <div class="form-group">
            <label for="surname"><fmt:message key="label.surname"/></label>
            <input type="text" class="form-control" id="surname"
                   name="surname" value="${person.surname}"/>
        </div>
        <br>
        <div class="form-group">
            <label for="patronymic"><fmt:message key="label.patronymic"/></label>
            <input type="text" class="form-control" id="patronymic"
                   name="patronymic" value="${person.patronymic}"/>
        </div>
        <br>
        <div class="form-group">
            <label for="dateOfBirth"><fmt:message key="label.date_of_birth"/></label>
            <input type="date" class="form-control" id="dateOfBirth"
                   name="dateOfBirth" value="${person.dateOfBirth}"/>
        </div>
        <br>
        <div class="form-group">
            <label for="address"><fmt:message key="label.address"/></label>
            <input type="text" class="form-control" id="address"
                   name="address" value="${person.address}"/>
        </div>
        <br>
        <div class="form-group">
            <label for="phone"><fmt:message key="label.phone"/></label>
            <input type="tel" class="form-control" id="phone"
                   name="phone" value="${person.phone}"/>
        </div>
        <br>
        <c:choose>
            <c:when test="${sessionScope.authorizedUser.role.name().equals('TRAINER')}">
                <div class="form-group">
                    <label for="achievements">Comment:</label>
                    <textarea class="form-control" rows="5" id="achievements"
                              name="achievements">${person.achievements}</textarea>
                </div>
            </c:when>
            <c:otherwise>
                <div class="checkbox">
                    <label for="share">
                        <input type="checkbox" name="share" value="true"
                               id="share" checked="${person.achievements}"><fmt:message key="checkbox.remember_me"/>
                    </label>
                </div>
            </c:otherwise>
        </c:choose>

        <p id="errorMessage" class="text-danger"></p>
        <br>
        <p class="text-danger">${requestScope.warning_message}</p>

        <button type="submit" class="btn btn-success"><fmt:message key="button.save_changes"/></button>
    </div>
</form>


</body>
</html>
