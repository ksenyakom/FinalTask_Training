<%--
  Author: Ksiniya Oznobishina
  Date: 05.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--Локализация--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.complex.list"/></title>
    <%@include file="/WEB-INF/jsp/common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>

</head>
<body>

<c:import url="/WEB-INF/jsp/common/main_menu.jsp"/>

<div class="container-fluid text-center">
    <div class="row content">
        <%--side menu of the page--%>
        <c:import url="/WEB-INF/jsp/common/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8 text-justify">
            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                    <caption>
                        <h4><fmt:message key="complex.table.name"/></h4>
                    </caption>
                    <tr class="active">
                        <th scope="col">№</th>
                        <th scope="col"><fmt:message key="table.training_title"/></th>
                        <th scope="col"><fmt:message key="table.trainer_developed"/></th>
                        <th scope="col"><fmt:message key="table.rating"/></th>
                    </tr>
                    <c:forEach items="${lst}" var="complex" varStatus="status">
                        <tr <c:if test="${complex.rating > 4}">class="success"</c:if>>
                            <td>${ status.count }</td>
                            <td>${ complex.title }</td>
                            <td>${ complex.trainerDeveloped.login }</td>
                            <td><fmt:formatNumber value="${ complex.rating }"/></td>
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
