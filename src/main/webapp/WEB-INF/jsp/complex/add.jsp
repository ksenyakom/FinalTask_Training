<%--
  Author: Ksiniya Oznobishina 
  Date: 27.01.2021
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/validator_complex.js"/>"></script>
    <title><fmt:message key="title.complex_add"/></title>
</head>
<body>
<%@ include file="../common/main_menu.jsp" %>
<c:set var="isAdmin"
       value="${sessionScope.authorizedUser.role.name().equals('ADMINISTRATOR')}"/>
<div class="container-fluid">
    <div class="row content">

        <div class="col-sm-1"></div>
        <div class="col-sm-11">
            <h3 class="text-center"><fmt:message key="title.complex_add"/></h3>
            <%@ include file="../common/messages.jsp" %>

            <form action='<c:url value="save.html"/>' method="post" enctype="multipart/form-data"
                  onsubmit="return validateComplex(this)">
                <label for="title"><fmt:message key="label.complex_title"/></label>
                <input type="text" class="form-control" id="title" name="title" value="${complex.title}"/>
                <br>
                <fmt:message key="label.status"/>
                <c:choose>
                    <c:when test="${isAdmin}">
                        <fmt:message key="label.common"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="label.visitor_for"/>
                        <div class="table-responsive">
                            <table class="table table-hover table-bordered">
                                <tr class="active">
                                    <th scope="col">№</th>
                                    <th scope="col"><fmt:message key="table.choose"/></th>
                                    <th scope="col"><fmt:message key="table.visitor.login"/></th>
                                </tr>
                                <c:forEach items="${lst}" var="visitor" varStatus="status">
                                    <tr>
                                        <td>${ status.count }</td>
                                        <td><input type="radio" name="visitorId" value="${visitor.id}" required></td>
                                        <td>${ visitor.login }</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
                <br>
                <br>
                <button type="submit" class="btn btn-success"><fmt:message key="button.create"/></button>
            </form>
            <p id="errorMessage" class="text-danger"></p>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>
