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
            <c:url value="save_new.html" var="myURL">
                  <c:param name = "previousEndDate" value = "${subscription.endDate}"/>
            </c:url>
            <form action="${myURL}" method="POST" onsubmit="return validateSubscriptionNew(this)">
                <div class="container col-xs-8">
                    <h3><fmt:message key="title.subscription_buy"/></h3>
                    <br>
                    <c:choose>
                        <c:when test="${empty subscription}">
                            <p>Нет активной подписки</p>
                        </c:when>
                        <c:otherwise>
                            <p>Текущая активная подписка: ${subscription.beginDate} - ${subscription.endDate}</p>
                        </c:otherwise>
                    </c:choose>
                    <hr>
                    <div class="form-group">
                        <label for="beginDate">Дата начала новой подписки</label>
                        <input type="date" class="form-control" id="beginDate"
                               name="beginDate"  required>
                    </div>
                    <p> Продолжительность</p>
                    <div class="radio">
                        <label><input type="radio" name="period" value="1" checked>1 месяц - 100р</label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="period" value="2">2 месяца - 150р</label>
                    </div>
                    <div class="radio">
                        <label><input type="radio" name="period" value="3">3 месяца - 200р</label>
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
