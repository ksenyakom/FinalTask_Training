<%--
  Author: Ksiniya Oznobishina 
  Date: 25.01.2021
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.exercise_edit"/></title>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<c:url value="/js/main.js"/>"></script>
</head>
<body>
<%@ include file="../common/main_menu.jsp" %>
<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-1"></div>
        <div class="col-sm-11">
            <h4 class="text-center"><fmt:message key="title.exercise_edit"/></h4>
            <%@ include file="../common/messages.jsp" %>
            <c:url value="edit.html" var="myURL">
<%--                <c:param />--%>
<%--                <c:param name="picturePath" value="${exercise.picturePath}"/>--%>
            </c:url>
            <form action='${myURL}' method="post" enctype="multipart/form-data">
                <input type="hidden" name="exerciseId" value="${exercise.id}">
                <input type="hidden" name="picturePath" value="${exercise.picturePath}">
                <input type="hidden" name="currentPage" value="${currentPage}">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label for="title"><fmt:message key="label.exercise_title"/></label>
                        <input type="text" class="form-control" id="title"
                               name="title" value="${exercise.title}"/>
                    </div>
                    <div class="form-group">
                        <label for="adjusting"><fmt:message key="table.adjusting"/></label>
                        <textarea class="form-control" rows="5" id="adjusting"
                                  name="adjusting">${exercise.adjusting}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="mistakes"><fmt:message key="table.mistakes"/></label>
                        <textarea class="form-control" rows="5" id="mistakes"
                                  name="mistakes">${exercise.mistakes}</textarea>
                    </div>
                    <table class="table table-hover table-bordered">
                        <caption>

                        </caption>
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="table.choose"/></th>
                            <th scope="col"><fmt:message key="table.type"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="exerciseType" varStatus="status">
                            <tr>
                                <td>${ status.count }</td>
                                <td><input type="radio" name="exerciseType" value="${exerciseType}"
                                           <c:if test="${exercise.type.equals(exerciseType)}">checked</c:if>
                                           required></td>
                                <td>${ exerciseType }</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="table-responsive col-sm-5">

                    <img src='<c:url value="${ exercise.picturePath }"/>'
                         class="img-responsive center-block" width=200 alt="picture of exercise"/></td>
                    <div class="form-group">
                        <label for="picture"><fmt:message key="label.picture"/></label>
                        <input type="file" name="picture" id="picture"/>
                    </div>

                    <%--                    <div class="form-group" >--%>
                    <%--                        <label for="audio"><fmt:message key="label.audio"/></label>--%>
                    <%--                        <input type="file"  name="audio" id="audio"/>--%>
                    <%--                    </div>--%>
                </div>
                <p id="errorMessage" class="text-danger"></p>
                <button type="submit" value="Upload file" class="btn btn-success"><fmt:message
                        key="button.save"/></button>
            </form>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>
