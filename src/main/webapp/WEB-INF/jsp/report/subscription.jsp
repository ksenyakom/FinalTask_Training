<%--
  Author: Ksiniya Oznobishina 
  Date: 10.02.2021
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
    <title><fmt:message key="title.reports"/></title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/validator_report.js"/>"></script>

</head>
<body>
<%@ include file="../common/main_menu.jsp" %>
<div class="container-fluid">
    <div class="row content">
        <%-- menu of the page--%>
        <c:import url="../admin/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8">
            <h2><fmt:message key="title.reports"/></h2>
            <hr>
            <form class="form-inline" onsubmit="return validateReport(this)"
                  action='<c:url value = "subscription.html"/>' method="get">
                <label><fmt:message key="label.by_date"/>:</label>
                <br>
                <label for="beginDate"><fmt:message key="label.from"/></label>
                <input type="date" class="form-control" width=200px id="beginDate" name="from" value="${from}">
                <label for="endDate"><fmt:message key="label.to"/></label>
                <input type="date" class="form-control" id="endDate" name="to" value="${to}">
                <br>
                <br>
                <label for="userLogin"><fmt:message key="label.by_login"/>: </label>
                <br>
                <input type="text" class="form-control" id="userLogin" name="login" value="${login}">
                <br>
                <br>
                <p id="errorMessage" class="text-danger"></p>
                <br>
                <button type="submit" class="btn btn-success"><fmt:message key="button.show"/></button>
            </form>
            <hr>
            <c:import url="../common/messages.jsp"/>
            <div class="col-sm-12 table-responsive">
                <table class="table table-hover table-bordered">
                    <caption>

                    </caption>
                    <tr class="active">
                        <th scope="col">№</th>
                        <th scope="col"><fmt:message key="table.login"/></th>
                        <th scope="col"><fmt:message key="table.begin_date"/></th>
                        <th scope="col"><fmt:message key="table.end_date"/></th>
                        <th scope="col"><fmt:message key="table.price"/></th>
                    </tr>
                    <c:forEach items="${lst}" var="subscription" varStatus="status">
                        <tr>
                            <td>${ status.count }</td>
                            <td>${ subscription.visitor.login }</td>
                            <td><ctg:parse localDate="${ subscription.beginDate }"
                                           language="${cookie.language.value}"/></td>
                            <td><ctg:parse localDate="${ subscription.endDate }"
                                           language="${cookie.language.value}"/></td>
                            <td><fmt:formatNumber value="${ subscription.price }"/></td>
                        </tr>
                    </c:forEach>
                </table>
                <p>Total : ${lst.size()}</p>
                <p>Sum : ${sum}</p>

            </div>
        </div>
        <div class="col-sm-2 sidenav">
            <%@ include file="../common/ads.jsp" %>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>

