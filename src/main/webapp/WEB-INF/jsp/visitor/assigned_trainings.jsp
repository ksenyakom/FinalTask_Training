<%--
  Author: Ksiniya Oznobishina 
  Date: 17.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
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

<c:import url="/WEB-INF/jsp/common/main_menu.jsp"/>

<div class="container-fluid text-center">
    <div class="row content">
        <%--side menu of the page--%>
        <c:import url="/WEB-INF/jsp/visitor/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8 text-justify">
            <c:import url="/WEB-INF/jsp/common/messages.jsp"/>
            <h2><fmt:message key="complex.table.training_title"/></h2>

            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                    <caption>
                        <h4><fmt:message key="table.name.assigned"/></h4>
                    </caption>
                    <tr class="active">
                        <th scope="col">№</th>
                        <th scope="col"><fmt:message key="complex.table.training_title"/></th>
                        <th scope="col"><fmt:message key="complex.table.date_expected"/></th>
                        <th scope="col"><fmt:message key="complex.table.date_executed"/></th>
                    </tr>
                    <c:forEach items="${lst}" var="assignedComplex" varStatus="status">
                        <tr>
                            <td>${ status.count }</td>
                            <td>${ assignedComplex.complex.title }</td>
                            <td><ctg:parse localDate="${ assignedComplex.dateExpected }"
                                           language="${cookie.language.value}"/></td>
                            <td><c:choose>
                                <c:when test="${not empty assignedComplex.dateExecuted}">
                                    <ctg:parse localDate="${ assignedComplex.dateExecuted }"
                                               language="${cookie.language.value}"/>
                                </c:when>
                                <c:otherwise>
                                    <a href='<c:url
                                            value="../complex/execute.html?assignedComplexId=${assignedComplex.id}"/>'>
                                            <fmt:message key="table.execute"/></a>
                                </c:otherwise>
                            </c:choose>
                            </td>

                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="col-sm-2 sidenav">
            <c:import url="/WEB-INF/jsp/common/ads.jsp"/>
        </div>
    </div>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"/>

</body>
</html>
