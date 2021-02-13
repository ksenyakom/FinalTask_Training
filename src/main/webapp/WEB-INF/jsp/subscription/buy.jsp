<%--
  Author: Ksiniya Oznobishina 
  Date: 18.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.subscription_buy"/></title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/validator_subscription.js"/>"></script>

</head>
<body>
<%@ include file="../common/main_menu.jsp" %>
<div class="container-fluid">
    <div class="row content">
        <%-- menu of the page--%>
        <c:import url="../visitor/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8">
            <c:import url="../common/messages.jsp"/>

            <c:url value="save_new.html" var="myURL">
                  <c:param name = "previousEndDate" value = "${subscription.endDate}"/>
            </c:url>
            <form action="${myURL}" method="POST" onsubmit="return validateSubscriptionNew(this)">
                <div class="container col-xs-8">
                    <h3><fmt:message key="title.subscription_buy"/></h3>

                    <br>
                    <c:choose>
                        <c:when test="${empty subscription}">
                            <p><fmt:message key="message.no_active_subscription"/></p>
                        </c:when>
                        <c:otherwise>
                            <p><fmt:message key="message.active_subscription"/>: ${subscription.beginDate} - ${subscription.endDate}</p>
                        </c:otherwise>
                    </c:choose>
                    <hr>
                    <div class="form-group">
                        <label for="beginDate"><fmt:message key="label.new_subscription_begin_date"/> </label>
                        <input type="date" class="form-control" id="beginDate"
                               name="beginDate"  required>
                    </div>
                    <p> <fmt:message key="label.duration"/></p>
                    <div class="radio ">
                        <p> <label><input type="radio" name="period" value="1" checked>1 <fmt:message key="word.month"/>  - 100р</label></p>
                        <p><label><input type="radio" name="period" value="2">2 <fmt:message key="word.months"/> - 150р</label></p>
                        <p><label><input type="radio" name="period" value="3">3 <fmt:message key="word.months"/> - 200р</label></p>
                    </div>
                    <br>
                    <p id="errorMessage" class="text-danger"></p>
                    <br>
                    <button type="submit" class="btn btn-success"><fmt:message key="button.buy"/></button>
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
