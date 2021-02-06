<%--
  Author: Ksiniya Oznobishina 
  Date: 25.01.2021
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
    <%@include file="/WEB-INF/jsp/common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>
    <title><fmt:message key="title.complex.list"/></title>

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
            <%@ include file="../common/messages.jsp" %>

            <a href='<c:url value="add.html"/>' class="btn btn-success" role="button">
                <fmt:message key="button.add"/></a>
            <form onsubmit="return validateDelete(this)" enctype="multipart/form-data">
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
                            <tr <c:choose>
                                <c:when test="${not empty complex.visitorFor}">class="warning"</c:when>
                                <c:when test="${ empty complex.visitorFor}">class="success"</c:when>
                                <c:otherwise>class="text-muted"</c:otherwise>
                            </c:choose>>
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
                                        <c:url value="edit.html" var="myURL">
                                            <c:param name="complexId" value="${complex.id}"/>
                                        </c:url>
                                        <td><a href='${myURL}'>
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
