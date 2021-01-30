<%--
  Author: Ksiniya Oznobishina 
  Date: 27.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/validator_complex.js"/>"></script>

    <title><fmt:message key="title.complex_edit"/></title>
</head>
<body>
<%@ include file="../common/main_menu.jsp" %>
<div class="container-fluid">
    <div class="row content">

        <div class="col-sm-1">
        </div>
        <div class="col-sm-11">
            <%--Message--%>
            <h3 class="text-center"><fmt:message key="title.complex_edit"/></h3>
                <%@ include file="../common/messages.jsp" %>

            <form action='<c:url value="edit.html"/>' method="post" enctype="multipart/form-data" onsubmit="return validateComplex(this)">
                <input type="hidden" name="complexId" value="${ complex.id }">
                <c:if test="${not empty complex.visitorFor}">
                    <input type="hidden" name="visitorId" value="${complex.visitorFor}"></c:if>
                <label for="title"><fmt:message key="label.complex_title"/></label>
                <input type="text" class="form-control" id="title" name="title" value="${complex.title}"/>
                <br>
                <fmt:message key="label.status"/>
                <c:choose>
                    <c:when test="${empty complex.visitorFor}">
                        <fmt:message key="label.common"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="label.visitor_for"/> ${ complex.visitorFor.login }
                    </c:otherwise>
                </c:choose>
                <br>
                <br>

                <button type="submit" class="btn btn-success"><fmt:message key="button.update_title_and_exit"/></button>
            </form>

            <form onsubmit="return validateDelete(this)">
                <div class="form-group">
                </div>
                <table class="table table-hover table-bordered">
                    <caption>
                        <h4><fmt:message key="label.side_menu.exercise_list"/></h4>
                    </caption>
                    <tr class="active">
                        <th scope="col">№</th>
                        <th scope="col">№ edit</th>
                        <th scope="col"><fmt:message key="table.title"/></th>
                        <th scope="col"><fmt:message key="table.repeat"/></th>
                        <th scope="col"><fmt:message key="table.group"/></th>
                        <th scope="col"><fmt:message key="table.remove_exercise"/></th>
                        <th scope="col"><fmt:message key="table.picture"/></th>
                        <th scope="col"><fmt:message key="table.adjusting"/></th>
                    </tr>
                    <c:forEach items="${complex.listOfUnits}" var="unit" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td><input disabled type="number" class="form-control" name="number" value="${status.count}"/></td>
                            <td>${unit.exercise.title}</td>
                            <td><input disabled type="number" class="form-control" name="repeat" value="${unit.repeat}"/></td>
                            <td><input disabled type="number" class="form-control" name="group" value="${unit.group}"/></td>
                            <td><input type="checkbox" class="require-one" name="remove" value="${status.count}"/>
                            </td>
                            <td><img src='<c:url value="${ unit.exercise.picturePath }"/>'
                                     class="img-responsive center-block" width=100 alt="picture"/></td>
                            <td>${unit.exercise.adjusting}</td>
                        </tr>
                    </c:forEach>
                </table>

                <c:url value="add_exercise_in_complex.html" var="myURL">
                    <c:param name="complexId" value="${ complex.id }"/>
                </c:url>
                <a href='<c:url value="${ myURL }"/>' class="btn btn-success" role="button"><fmt:message
                        key="table.add_exercise"/></a>

                <c:url value="delete_exercise_in_complex.html" var="myURL">
                    <c:param name="complexId" value="${ complex.id }"/>
                </c:url>
                <button type="submit" class="btn btn-warning" formmethod="post"
                        formaction="${ myURL }">
                    <fmt:message key="table.remove_exercise"/></button>
                <br>

            </form>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>
