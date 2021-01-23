<%--
  Author: Ksiniya Oznobishina 
  Date: 22.01.2021
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
    <title><fmt:message key="title.complex.list"/></title>
    <%@include file="/WEB-INF/jsp/common/head.jsp" %>
</head>
<body>

<c:import url="/WEB-INF/jsp/common/main_menu.jsp"/>

<div class="container-fluid text-center">
    <div class="row content">
        <%--side menu of the page--%>
        <c:import url="/WEB-INF/jsp/trainer/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8 text-justify">

            <form onsubmit="return validateDelete(this)">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <caption>
                            <h4><fmt:message key="table.name.assigned"/></h4>
                            <p><fmt:message key="table.visitor.login"/>: ${ visitor.login }</p>
                            <c:url value="add.html" var="myURL">
                                <c:param name="userId" value="${ visitor.id }"/>
                            </c:url>
                            <a href='<c:url value="${myURL}"/>' class="btn btn-success" role="button"><fmt:message
                                    key="button.add"/></a>
                            <br>
                        </caption>
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="complex.table.training_title"/></th>
                            <th scope="col"><fmt:message key="complex.table.date_expected"/></th>
                            <th scope="col"><fmt:message key="complex.table.date_executed"/></th>
                            <th scope="col"><fmt:message key="table.edit"/></th>
                            <th scope="col"><fmt:message key="table.remove"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="assignedComplex" varStatus="status">
                            <tr>
                                <td>${ status.count }</td>
                                <td>${ assignedComplex.complex.title }</td>
                                <td><ctg:parse localDate="${ assignedComplex.dateExpected }"
                                               language="${cookie.language.value}"/></td>
                                <td><ctg:parse localDate="${ assignedComplex.dateExecuted }"
                                               language="${cookie.language.value}"/></td>
                                <td><c:if test="${ empty assignedComplex.dateExecuted}">
                                    <a href='<c:url value="edit.html?assignedComplexId=${assignedComplex.id}"/>'>
                                        <fmt:message key="table.edit"/></a> </c:if></td>
                                <td><input type="checkbox" class="require-one" name="remove"
                                           value="${assignedComplex.id}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <button type="submit" class="btn btn-warning" formmethod="post" formaction="delete.html"
                            name="userId" value="${visitor.id}">
                        <fmt:message key="table.remove"/></button>
                    <br>
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
