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
    <title><fmt:message key="title.my_trainable"/></title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>

</head>
<body>
<%@ include file="../common/main_menu.jsp" %>
<div class="container-fluid">
    <div class="row content">
        <%-- menu of the page--%>
        <c:import url="../trainer/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8">
            <%--Message--%>
            <c:if test="${not empty warningMessage}"><p class="text-warning"><fmt:message
                    key="${ warningMessage }"/></p></c:if>
            <c:if test="${not empty successMessage}"><p class="text-success"><fmt:message
                    key="${ successMessage }"/></p></c:if>

            <h4 class="text-center"><fmt:message key="title.my_trainable"/></h4>
            <br>
            <form onsubmit="return validateDelete(this)">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="table.login"/></th>
                            <th scope="col"><fmt:message key="table.begin_date"/></th>
                            <th scope="col"><fmt:message key="table.end_date"/></th>
                            <th scope="col"><fmt:message key="table.edit"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="subscription" varStatus="status">
                            <tr>
                                <td>${ status.count }</td>
                                <td>${ subscription.visitor.login }</td>
                                <td><ctg:parse localDate="${ subscription.beginDate }"
                                               language="${cookie.language.value}"/></td>
                                <td><ctg:parse localDate="${ subscription.endDate }"
                                               language="${cookie.language.value}"/></td>
                                <td>
                                    <a href='<c:url value="/assigned_complex/list.html?userId=${subscription.visitor.id}"/>'>
                                        <fmt:message key="table.name.assigned"/></a>
                                </td>

                                    <%--                                <td><input id="remove" class="require-one" type="checkbox" name="remove" value="${user.id}"/></td>--%>
                            </tr>
                        </c:forEach>
                    </table>
                    <%--                    <button type="submit" class="btn btn-warning" formaction="deleteUser.html" formmethod="post"--%>
                    <%--                            name="role" value="${param.get("role")}" ><fmt:message key="table.remove"/></button>--%>
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
