<%--
  Author: Ksiniya Oznobishina 
  Date: 14.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.subscription_edit"/></title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/validator_subscription.js"/>"></script>

</head>
<body>
<%@ include file="../common/main_menu.jsp" %>
<div class="container-fluid">
    <div class="row content">
        <%-- menu of the page--%>
        <c:import url="../admin/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8">

            <c:url value = "update.html" var = "myURL">
                <c:param name = "subscriptionId" value = "${subscription.id}"/>
                <c:param name = "visitorId" value = "${subscription.visitor.id}"/>
            </c:url>
            <form action="${myURL}" method="POST" onsubmit="return validateSubscriptionEdit(this)">
                <input type="hidden" name="action" value="${action}">

                <h2><fmt:message key="title.subscription_edit"/></h2>
                <br>
                <div class="container col-xs-6">

                    <p>Visitor: ${subscription.visitor.login}</p>
                    <div class="form-group">
                        <label for="beginDate">Дата начала</label>
                        <input type="date" class="form-control" id="beginDate"
                               name="beginDate" value="${subscription.beginDate}" required>
                    </div>
                    <div class="form-group">
                        <label for="endDate">Дата окончания</label>
                        <input type="date" class="form-control" id="endDate"
                               name="endDate" value="${subscription.endDate}" required>
                    </div>
                    <br>
                    <div class="form-group">
                        <label for="price">Стоимость</label>
                        <input type="number" class="form-control" id="price"
                               name="price" value="${subscription.price}" required>
                    </div>
                    <br>
                    <p id="errorMessage" class="text-danger"></p>
                    <br>
                    <button type="submit" class="btn btn-success"><fmt:message key="button.save"/></button>
                </div>
            </form>
        </div>
        <div class="col-sm-2 sidenav">
            <%@ include file="../common/ads.jsp" %>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>


<%--                            <td>--%>
<%--                                <fmt:parseDate value="${subscription.beginDate}" pattern="yyyy-MM-dd"--%>
<%--                                               var="parsedBeginDate" type="date"/>--%>
<%--                                <fmt:formatDate value="${parsedBeginDate}" type="date" dateStyle="short"/>--%>
<%--                            </td>--%>
<%--                            <td>--%>
<%--                                <fmt:parseDate value="${subscription.endDate}" pattern="yyyy-MM-dd" var="parsedEndDate"--%>
<%--                                               type="date"/>--%>
<%--                                <fmt:formatDate value="${parsedEndDate}" type="date" dateStyle="short"/>--%>
<%--                            </td>--%>
<%--                            <td><fmt:formatNumber value="${ subscription.price }"/></td>--%>

