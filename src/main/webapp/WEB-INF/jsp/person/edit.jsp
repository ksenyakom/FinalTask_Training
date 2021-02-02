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
    <script type="text/javascript" src="<c:url value="/js/validator_person.js"/>"></script>

</head>

<body>
<%@ include file="../common/main_menu.jsp" %>
<%-- --%>
<form action="<c:url value="save_changes.html"/>" method="POST" enctype="multipart/form-data"
      onsubmit="return validatePerson(this)">
    <div class="container">
        <h2><fmt:message key="title.edit_person"/></h2>
        <br>
        <div class="form-group">
            <label for="name"><fmt:message key="label.name"/>*</label>
            <input type="text" class="form-control" id="name"
                   name="name" value="${person.name}"/>
        </div>
        <div class="form-group">
            <label for="surname"><fmt:message key="label.surname"/></label>
            <input type="text" class="form-control" id="surname"
                   name="surname" value="${person.surname}"/>
        </div>
        <div class="form-group">
            <label for="patronymic"><fmt:message key="label.patronymic"/></label>
            <input type="text" class="form-control" id="patronymic"
                   name="patronymic" value="${person.patronymic}"/>
        </div>
        <div class="form-group">
            <label for="dateOfBirth"><fmt:message key="label.date_of_birth"/></label>
            <input type="date" class="form-control" id="dateOfBirth"
                   name="dateOfBirth" value="${person.dateOfBirth}"/>
        </div>
        <div class="form-group">
            <label for="address"><fmt:message key="label.address"/></label>
            <input type="text" class="form-control" id="address"
                   name="address" value="${person.address}"/>
        </div>
        <div class="form-group">
            <label for="phone"><fmt:message key="label.phone"/></label>
            <input type="tel" pattern="\+[0-9]{12}" class="form-control" id="phone"
                   name="phone" value="${person.phone}"/>
            <small><fmt:message key="hint.phone"/> </small>
        </div>
        <br>
<%--        <c:choose>--%>
<%--            <c:when test="${sessionScope.authorizedUser.role.name().equals('TRAINER')}">--%>
<%--                <div class="form-group">--%>
<%--                    <label for="achievements"><fmt:message key="label.achievements"/></label>--%>
<%--                    <textarea class="form-control" rows="5" id="achievements"--%>
<%--                              name="achievements">${person.achievements}</textarea>--%>
<%--                </div>--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--                <div class="checkbox">--%>
<%--                    <label for="share">--%>
<%--                        <input type="checkbox" name="share" value="true"--%>
<%--                               id="share" checked="${person.achievements}"><fmt:message key="checkbox.share"/>--%>
<%--                    </label>--%>
<%--                </div>--%>
<%--            </c:otherwise>--%>
<%--        </c:choose>--%>

        <p id="errorMessage" class="text-danger"></p>
        <%@ include file="../common/messages.jsp" %>

        <button type="submit" class="btn btn-success"><fmt:message key="button.save_changes"/></button>
    </div>
</form>


</body>
</html>
