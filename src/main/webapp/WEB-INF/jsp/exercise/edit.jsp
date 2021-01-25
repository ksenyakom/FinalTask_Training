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
        <%-- menu of the page--%>
        <%--        <c:import url="../trainer/side_menu.jsp"/>--%>
        <%--Content of the page --%>
        <div class="col-sm-1">
        </div>
        <div class="col-sm-11">
            <%--Message--%>
            <h4 class="text-center"><fmt:message key="title.exercise_edit"/></h4>
            <c:if test="${not empty warningMessage}"><p class="text-warning"><fmt:message
                    key="${ warningMessage }"/></p></c:if>
            <c:if test="${not empty successMessage}"><p class="text-success"><fmt:message
                    key="${ successMessage }"/></p></c:if>
            <c:url value="edit.html" var="myURL">
                <c:param name="exerciseId" value="${exercise.id}"/>
                <c:param name="picturePath" value="${exercise.picturePath}"/>
            </c:url>
            <form action='${myURL}' method="post" enctype="multipart/form-data">
                <div class="col-sm-6">
                    <div class="form-group">
                        <label for="title"><fmt:message key="label.exercise_title"/></label>
                        <input type="text" class="form-control" id="title"
                               name="title" value="${exercise.title}"/>
                    </div>
                    <div class="form-group">
                        <label for="adjusting">Отстройка</label>
                        <textarea class="form-control" rows="5" id="adjusting"
                                  name="adjusting">${exercise.adjusting}</textarea>
                    </div>
                    <div class="form-group">
                        <label for="mistakes">Ошибки</label>
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
                                <td><input type="radio" name="exerciseType" value="${exerciseType}"  checked="${exercise.type == 'exerciseType'}"
                                           required></td>
                                <td>${ exerciseType }</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="table-responsive col-sm-5">

                    <img src='<c:url value="${ exercise.picturePath }"/>'
                         class="img-responsive center-block" width=200 alt="picture"/></td>
                    <div class="form-group">
                        <label for="picture"><fmt:message key="label.picture"/></label>
                        <input type="file" name="picture" id="picture"/>
                    </div>

                    <%--                    <div class="form-group" >--%>
                    <%--                        <label for="audio"><fmt:message key="label.audio"/></label>--%>
                    <%--                        <input type="file"  name="audio" id="audio"/>--%>
                    <%--                    </div>--%>
                </div>
                <button type="submit" value="Upload file" class="btn btn-success"><fmt:message
                        key="button.save"/></button>
            </form>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>
