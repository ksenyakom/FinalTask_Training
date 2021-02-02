<%--
  Author: Ksiniya Oznobishina 
  Date: 27.01.2021
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
    <title><fmt:message key="title.exercise_list"/></title>
    <%@include file="/WEB-INF/jsp/common/head.jsp" %>
</head>
<body>

<c:import url="/WEB-INF/jsp/common/main_menu.jsp"/>

<div class="container-fluid text-center">
    <div class="row content">
        <%--side menu of the page--%>

        <%--Content of the page --%>
        <div class="col-sm-10 text-justify">
            <h2><fmt:message key="title.exercise_list"/></h2>
            <c:if test="${not empty warningMessage}">
                <p class="text-danger"><fmt:message key="${warningMessage}"/></p>
            </c:if>
            <c:if test="${not empty successMessage}">
                <p class="text-success"><fmt:message key="${successMessage}"/></p>
            </c:if>

            <form onsubmit="return validateAdd(this)">
                <div class="table-responsive">
                    <c:url value="add_exercise_in_complex.html" var="myUrl">
                        <c:param name="complexId" value="${complexId}"/>
                    </c:url>
                    <button type="submit" class="btn btn-success" formmethod="post" formaction="${myUrl}">
                        <fmt:message key="table.add_exercise"/></button>

                    <table class="table table-hover table-bordered">
                        <caption>
                        </caption>
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="table.add_exercise"/></th>
                            <th scope="col"><fmt:message key="table.title"/></th>
                            <th scope="col"><fmt:message key="table.adjusting"/></th>
                            <th scope="col"><fmt:message key="table.picture"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="exercise" varStatus="status">
                            <tr>
                                <td>${ status.count }</td>
                                <td><input type="checkbox" class="require-one" name="addId" value="${exercise.id}"/></td>
                                <td>${ exercise.title }</td>
                                <td>${ exercise.adjusting }</td>

                                <td><img src='<c:url value="${ exercise.picturePath }"/>'
                                         class="img-responsive center-block" width=100 alt="picture"/></td>
                            </tr>
                        </c:forEach>
                    </table>

                </div>
            </form>
        </div>

    </div>
</div>

<c:import url="/WEB-INF/jsp/common/footer.jsp"/>

</body>
</html>
