<%--
  Author: Ksiniya Oznobishina 
  Date: 25.01.2021
--%>
<%--
  Author: Ksiniya Oznobishina
  Date: 05.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--Локализация--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.complex.list"/></title>
    <%@include file="/WEB-INF/jsp/common/head.jsp" %>
</head>
<body>
<c:set var="isAdmin"
       value="${sessionScope.authorizedUser.role.name().equals('ADMINISTRATOR')}"/>

<c:import url="/WEB-INF/jsp/common/main_menu.jsp"/>

<div class="container-fluid text-center">
    <div class="row content">
        <%--side menu of the page--%>
        <c:choose>
            <c:when test="${isAdmin}">
                <c:import url="/WEB-INF/jsp/admin/side_menu.jsp"/>
            </c:when>
            <c:otherwise>
                <c:import url="/WEB-INF/jsp/trainer/side_menu.jsp"/>
            </c:otherwise>
        </c:choose>
        <%--Content of the page --%>
        <div class="col-sm-8 text-justify">
            <c:if test="${not empty warningMessage}">
                <p class="text-danger"><fmt:message key="${warningMessage}"/></p></c:if>
            <c:if test="${not empty successMessage}">
                <p class="text-success"><fmt:message key="${successMessage}"/></p></c:if>

            <a href='<c:url value="add.html"/>' class="btn btn-success" role="button"><fmt:message
                    key="button.add"/></a>
            <form onsubmit="return validateDelete(this)">

                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <caption>
                            <h4><fmt:message key="complex.table.name"/></h4>
                        </caption>
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="table.training_title"/></th>
                            <th scope="col"><fmt:message key="table.trainer_developed"/></th>
                            <th scope="col"><fmt:message key="table.visitor_for"/></th>
                            <th scope="col"><fmt:message key="table.rating"/></th>
                            <th scope="col"><fmt:message key="table.edit"/></th>
                            <th scope="col"><fmt:message key="table.remove"/></th>
                            <th scope="col"><fmt:message key="table.execute"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="complex" varStatus="status">
                            <tr>
                                <td>${ status.count }</td>
                                <td>${ complex.title }</td>
                                <td>${ complex.trainerDeveloped.login }</td>
                                <td>${ complex.visitorFor.login }</td>
                                <td><fmt:formatNumber value="${ complex.rating }"/></td>
                                <c:choose>
                                    <c:when test="${ empty complex.visitorFor and isAdmin}">
                                        <td><a href='<c:url value="edit.html?complexId=${complex.id}"/>'>
                                            <fmt:message key="table.edit"/></a></td>
                                        <td><input type="checkbox" class="require-one" name="remove"
                                                   value="${complex.id}"/></td>
                                    </c:when>
                                    <c:when test="${not isAdmin and not empty complex.visitorFor}">
                                        <td><a href='<c:url value="edit.html?complexId=${complex.id}"/>'>
                                            <fmt:message key="table.edit"/></a></td>
                                        <td><input type="checkbox" class="require-one" name="remove"
                                                   value="${complex.id}"/></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td></td>
                                        <td></td>
                                    </c:otherwise>
                                </c:choose>

                                <td><a href='<c:url
                                            value="execute.html?complexId=${complex.id}"/>'>
                                    <fmt:message key="table.execute"/></a></td>

                            </tr>
                        </c:forEach>
                    </table>
                    <button type="submit" class="btn btn-warning" formmethod="post" formaction="delete.html">
                        <fmt:message key="table.remove"/></button>
                </div>
            </form>
        </div>
        <div class="col-sm-2 sidenav">
            <c:import url="/WEB-INF/jsp/common/ads.jsp"/>
        </div>
    </div>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"/>

</body>
</html>
