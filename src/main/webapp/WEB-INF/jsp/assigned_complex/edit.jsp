<%--
  Author: Ksiniya Oznobishina 
  Date: 22.01.2021
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
    <title><fmt:message key="title.assigned_complex_edit"/></title>
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
                <c:param name = "assignedComplexId" value = "${assignedComplex.id}"/>
                <c:param name = "visitorId" value = "${assignedComplex.visitor.id}"/>
            </c:url>
            <form action="${myURL}" method="POST" onsubmit="return validateAssignedComplexEdit(this)">
                <h2><fmt:message key="title.assigned_complex_edit"/></h2>
                <br>
                <div class="container col-xs-6">

                    <p class="text-info">Visitor: ${assignedComplex.visitor.login}</p>
                    <p class="text-info">Complex: ${assignedComplex.complex.title}</p>
                    <div class="form-group">
                        <label for="dateExpected">Дата начала</label>
                        <input type="date" class="form-control" id="dateExpected"
                               name="dateExpected" value="${assignedComplex.dateExpected}" required>
                    </div>
                    <br>
                    <table class="table table-hover table-bordered">
                        <caption>
                        </caption>
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="table.choose"/></th>
                            <th scope="col"><fmt:message key="complex.table.training_title"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="complex" varStatus="status">
                            <tr>
                                <td>${ status.count }</td>
                                <td><input type="radio" name="complexId" value="${complex.id}" required></td>
                                <td>${ complex.title }</td>
                            </tr>
                        </c:forEach>
                    </table>
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
