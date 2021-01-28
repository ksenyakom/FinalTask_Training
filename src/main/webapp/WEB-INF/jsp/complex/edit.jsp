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
    <title><fmt:message key="title.complex_edit"/></title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>
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
            <c:if test="${not empty warningMessage}"><p class="text-warning"><fmt:message
                    key="${ warningMessage }"/></p></c:if>
            <c:if test="${not empty successMessage}"><p class="text-success"><fmt:message
                    key="${ successMessage }"/></p></c:if>




            <c:url value="update.html" var="myURL">
                <c:param name="complexId" value="${ complex.id }"/>
            </c:url>
            <form action='${ myURL }' method="post" enctype="multipart/form-data" onsubmit="">
                <label for="title"><fmt:message key="label.complex_title"/></label>
                <input type="text" class="form-control" id="title" name="title" value="${complex.title}"/>
                <br>
                <fmt:message key="label.status"/>
                <c:choose>
                    <c:when test="${empty visitor}">
                        <fmt:message key="label.common"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="label.visitor_for"/> ${ visitor.login }
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
