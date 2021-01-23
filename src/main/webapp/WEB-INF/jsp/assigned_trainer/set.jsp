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
<html lang="ru">
<head>
    <title><fmt:message key="title.trainer_assignment"/></title>
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
            <%--Message--%>
                <c:if test="${not empty warningMessage}"><p class="text-warning"><fmt:message
                        key="${ warningMessage }"/></p></c:if>
                <c:if test="${not empty successMessage}"><p class="text-success"><fmt:message
                        key="${ successMessage }"/></p></c:if>
            <p class="text-success"><fmt:message key="label.visitor"/> ${ visitor.login }</p>
            <p class="text-success"><fmt:message key="label.trainer"/>
                <c:if test="${not empty trainer}">
                    ${ trainer.login }
                </c:if></p>

            <h4 class="text-center"><fmt:message key="title.trainer_assignment"/></h4>
            <br>
            <br>
            <c:url value="save.html" var="myURL">
                <c:param name="visitorId" value="${visitor.id}"/>
            </c:url>
            <form action="${myURL}" method="post">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="table.choose"/></th>
                            <th scope="col"><fmt:message key="table.trainer.login"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="tr" varStatus="status">
                            <tr>
                                <td>${ status.count }</td>
                                <td><input type="radio" name="trainerId" value="${tr.id}" required></td>
                                <td>${ tr.login }</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <button type="submit" class="btn btn-success"><fmt:message key="table.set"/></button>
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
