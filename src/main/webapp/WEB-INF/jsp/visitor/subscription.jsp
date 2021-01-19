<%--
  Author: Ksiniya Oznobishina 
  Date: 18.01.2021
--%>
<%--
  User: User
  Date: 14.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="${cookie.language.value}">
<%--проверить это--%>
<head>
    <title><fmt:message key="title.my_subscription"/></title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>

</head>
<body>
<%@ include file="../common/main_menu.jsp" %>
<div class="container-fluid">
    <div class="row content">
        <%-- menu of the page--%>
        <c:import url="../visitor/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8">
            <p class="text-success">${requestScope.success_message}</p>
            <hr>
            <h4 class="text-center"><fmt:message key="title.my_subscription"/></h4>
            <br>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="table.begin_date"/></th>
                            <th scope="col"><fmt:message key="table.end_date"/></th>
                            <th scope="col"><fmt:message key="table.price"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="subscription" varStatus="status">
                            <tr>
                                <td>${ status.count }</td>
                                <td><fmt:parseDate value="${subscription.beginDate}" pattern="yyyy-MM-dd"
                                                   var="parsedBeginDate" type="date"/>
                                    <fmt:formatDate value="${parsedBeginDate}" type="date" dateStyle="short"/>
                                </td>
                                <td>
                                    <fmt:parseDate value="${subscription.endDate}" pattern="yyyy-MM-dd"
                                                   var="parsedEndDate" type="date"/>
                                    <fmt:formatDate value="${parsedEndDate}" type="date" dateStyle="short"/>
                                </td>
                                <td><fmt:formatNumber value="${ subscription.price }"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>

            <a href='<c:url value="/subscription/buy.html"/>' class="btn btn-success" role="button"><fmt:message key="button.buy"/></a>
<%--            <button type="button" class="btn btn-success" formmethod="post" formaction="deleteSubscription.html"--%>
<%--                    name="action" value="${param.get("action")}"><fmt:message key="table.remove"/></button>--%>

        </div>
        <div class="col-sm-2 sidenav">
            <%@ include file="../common/ads.jsp" %>

        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
