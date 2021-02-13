<%--
  User: User
  Date: 27.12.2020
  Time: 22:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.journal"/></title>
    <%@ include file="common/head.jsp" %>
</head>
<body>
<c:import url="common/main_menu.jsp"/>

<div class="container-fluid text-center">
    <div class="row content">
        <%--side menu of the page--%>
        <c:import url="/WEB-INF/jsp/common/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8 text-justify">
            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                    <h4><fmt:message key="table.name.journal"/></h4>
                    <tr class="active">
                        <th scope="col">№</th>
                        <th scope="col"><fmt:message key="table.training_name"/></th>
                        <th scope="col"><fmt:message key="table.visitor.login"/></th>
                        <th scope="col"><fmt:message key="table.date"/></th>
                    </tr>
                    <c:forEach items="${lst}" var="assignedComplex" varStatus="status">
                        <tr>
                            <td>${ status.count }</td>
                            <td>${ assignedComplex.complex.title }</td>
                            <td>${ assignedComplex.visitor.login }</td>
                            <td><ctg:parse localDate="${ assignedComplex.dateExecuted }"
                                           language="${cookie.language.value}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

        </div>
        <div class="col-sm-2 sidenav">
            <c:import url="common/ads.jsp"/>
        </div>
    </div>
</div>

<c:import url="common/footer.jsp"/>
<br>
</body>
</html>
