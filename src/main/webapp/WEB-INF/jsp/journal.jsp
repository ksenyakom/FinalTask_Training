<%--
  User: User
  Date: 27.12.2020
  Time: 22:48
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
    <title><fmt:message key="title.journal"/></title>
    <%@ include file="common/head.jsp" %>

</head>
<body>
<%@ include file="common/main_menu.jsp" %>

<div class="container-fluid text-center">
    <div class="row content">
        <%--side menu of the page--%>
        <c:import url="/WEB-INF/jsp/common/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8 text-justify">
            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                    <h4><fmt:message key="journal.table.name"/></h4>
                    <tr class="active">
                        <th scope="col">№</th>
                        <th scope="col"><fmt:message key="journal.table.head.training_name"/></th>
                        <th scope="col"><fmt:message key="journal.table.head.user_login"/></th>
                        <th scope="col"><fmt:message key="journal.table.head.date"/></th>
                    </tr>
                    <c:forEach items="${lst}" var="assignedComplex" varStatus="status">
                        <tr>
                            <td><c:out value="${ status.count }"/></td>
                            <td><c:out value="${ assignedComplex.complex.title }"/></td>
                            <td><c:out value="${ assignedComplex.user.login }"/></td>
<%--                            <fmt:formatDate value="${ assignedComplex.dateExecuted }" var="date"/>--%>
<%--                            <td>--%>
<%--                                <ctg:parse localDate="${ assignedComplex.dateExecuted }" language="${cookie.language.value}"/>--%>
<%--                            </td>--%>
                            <td>
                                <fmt:parseDate value="${assignedComplex.dateExecuted}" pattern="yyyy-MM-dd"
                                               var="parsedDate" type="date" />

                                <fmt:formatDate value="${parsedDate}"
                                                type="date" dateStyle="short" />
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <hr>
            <h3>Test</h3>

        </div>
        <div class="col-sm-2 sidenav">
            <%@ include file="common/ads.jsp" %>
        </div>
    </div>
</div>

<%@ include file="common/footer.jsp" %>
<br>
</body>
</html>
