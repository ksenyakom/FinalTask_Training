<%--
  Author: Ksiniya Oznobishina 
  Date: 22.01.2021
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.complex_assignment"/></title>
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
            <%@ include file="../common/messages.jsp" %>

            <form action='<c:url value="add.html"/>' method="post" enctype="multipart/form-data">
                <input type="hidden" name="visitorId" value="${visitor.id}">
                <div class="form-group col-sm-5">
                    <h4 class="text-center"><fmt:message key="title.complex_assignment"/></h4>
                    <p class="text-info"><fmt:message key="label.visitor"/> ${ visitor.login }</p>
                    <label for="dateExpected"а><fmt:message key="label.training_date"/></label>
                    <input type="date" class="form-control" id="dateExpected"
                           name="dateExpected" required>
                    <%--                                <div disabled="true">--%>
                    <%--                                    <p><input id="frequency1" type="radio"><label for="frequency1">Один раз в неделю</label></p>--%>
                    <%--                                    <p><input id="frequency2" type="radio"><label for="frequency2">Два раза в неделю</label></p>--%>
                    <%--                                </div>--%>

                </div>
                <div class="table-responsive col-sm-12">
                    <table class="table table-hover table-bordered">
                        <caption>
                        </caption>
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="table.choose"/></th>
                            <th scope="col"><fmt:message key="complex.table.training_title"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="complex" varStatus="status">
                            <tr <c:choose>
                                <c:when test="${ not empty complex.visitorFor}">class="success"</c:when>
                                <c:otherwise>class="info"</c:otherwise>
                            </c:choose>>
                                <td>${ status.count }</td>
                                <td><input type="radio" name="complexId" value="${complex.id}" required></td>
                                <td>${ complex.title }</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <p id="errorMessage" class="text-danger"></p>
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
