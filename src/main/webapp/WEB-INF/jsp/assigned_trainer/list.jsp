<%--
  Author: Ksiniya Oznobishina 
  Date: 19.01.2021
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
    <title><fmt:message key="title.assigned_trainer_list"/></title>
    <%@ include file="../common/head.jsp" %>
        <script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>

</head>
<body>
<%@ include file="../common/main_menu.jsp" %>
<div class="container-fluid">
    <div class="row content">
        <%-- menu of the page--%>
        <c:import url="../admin/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8">
            <p class="text-success">${requestScope.success_message}</p>
            <hr>
            <h4 class="text-center"><fmt:message key="title.assigned_trainer_list"/></h4>
            <br>
            <br>
            <form action="list.html" method="GET">
                <div class="form-group">
                    <label class="control-label col-sm-offset-2 col-sm-2" for="action"><fmt:message
                            key="label.show"/></label>
                    <div class="col-sm-6 col-md-4">
                        <select id="action" class="form-control" name="action">
                            <option value="active"><fmt:message key="dropdown.active"/></option>
                            <option value="all"><fmt:message key="dropdown.all"/></option>
                            <option value="visitors_without_trainer"><fmt:message key="dropdown.visitors_without_trainer"/></option>
                        </select>
                    </div>
                </div>
                <input type="submit" value='<fmt:message key="button.show"/>'/></td>
            </form>

            <br>
            <form onsubmit="return validateDelete(this)">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <caption>
                        </caption>
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="table.trainer.login"/></th>
                            <th scope="col"><fmt:message key="table.visitor.login"/></th>
                            <th scope="col"><fmt:message key="table.begin_date"/></th>
                            <th scope="col"><fmt:message key="table.end_date"/></th>
                            <th scope="col"><fmt:message key="table.assign_trainer"/></th>
                            <th scope="col"><fmt:message key="table.remove"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="assign" varStatus="status">
                            <tr>
                                <td>${ status.count }</td>
                                <td>${ assign.trainer.login }</td>
                                <td>${ assign.visitor.login }</td>
                                <td><fmt:parseDate value="${assign.beginDate}" pattern="yyyy-MM-dd"
                                                   var="parsedBeginDate" type="date"/>
                                    <fmt:formatDate value="${parsedBeginDate}" type="date" dateStyle="short"/>
                                </td>
                                <td>
                                    <fmt:parseDate value="${assign.endDate}" pattern="yyyy-MM-dd"
                                                   var="parsedEndDate" type="date"/>
                                    <fmt:formatDate value="${parsedEndDate}" type="date" dateStyle="short"/>
                                </td>
                                <td>
                                    <c:if test="${empty assign.endDate}">
                                    <a href='<c:url value="/assigned_trainer/set.html?visitorId=${assign.visitor.id}"/>'>
                                        <fmt:message key="table.set"/></a>
                                    </c:if>
                                </td>
                                <td><input type="checkbox" class="require-one" name="remove"
                                           value="${assign.id}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <button type="submit" class="btn btn-warning" formmethod="post" formaction="delete.html"
                            name="action" value="${param.get("action")}">
                        <fmt:message key="table.remove"/></button>
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