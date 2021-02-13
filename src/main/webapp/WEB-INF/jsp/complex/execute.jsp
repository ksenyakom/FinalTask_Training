<%--
  Author: Ksiniya Oznobishina 
  Date: 20.01.2021
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
    <title><fmt:message key="title.complex.execute"/></title>
    <%@include file="/WEB-INF/jsp/common/head.jsp" %>
</head>
<body>
<c:set var="isAdmin" value="${sessionScope.authorizedUser.role.name().equals('ADMINISTRATOR') }"/>
<c:set var="isTrainer" value="${sessionScope.authorizedUser.role.name().equals('TRAINER')}"/>
<c:set var="isVisitor" value="${sessionScope.authorizedUser.role.name().equals('VISITOR') }"/>

<c:import url="/WEB-INF/jsp/common/main_menu.jsp"/>

<div class="container-fluid text-center">
    <div class="row content">
        <%--side menu of the page--%>
        <c:if test="${isVisitor}"><c:import url="/WEB-INF/jsp/visitor/side_menu.jsp"/></c:if>
        <c:if test="${isTrainer}"><c:import url="/WEB-INF/jsp/trainer/side_menu.jsp"/></c:if>
        <c:if test="${isAdmin}"><c:import url="/WEB-INF/jsp/admin/side_menu.jsp"/></c:if>
        <%--Content of the page --%>
        <div class="col-sm-8 text-justify">
            <h4><fmt:message key="title.complex.execute"/></h4>
            <p><fmt:message key="table.training_title"/> : ${complex.title}</p>
            <c:if test="${isVisitor}">
                <p><fmt:message key="table.date_expected"/> : ${assignedComplex.dateExpected}</p>

                <div class="container">
                    <form method="post" action='<c:url value="/assigned_complex/update_date_executed.html"/>'
                          enctype="multipart/form-data">
                        <input type="hidden" name="assignedComplexId" value="${assignedComplex.id}">
                        <button class="btn btn-success" disabled><fmt:message key="button.start"/></button>
                        <button class="btn btn-success" disabled><fmt:message key="button.pause"/></button>
                        <button class="btn btn-success" type="submit"><fmt:message key="button.finish"/></button>
                    </form>
                </div>
            </c:if>

            <div class="table-responsive">
                <table class="table table-hover table-bordered">
                    <caption>
                    </caption>
                    <tr class="active">
                        <th scope="col">№</th>
                        <th scope="col"><fmt:message key="table.title"/></th>
                        <th scope="col"><fmt:message key="table.adjusting"/></th>
                        <th scope="col"><fmt:message key="table.mistakes"/></th>
                        <th scope="col"><fmt:message key="table.picture"/></th>
                        <th scope="col"><fmt:message key="table.audio"/></th>
                    </tr>
                    <c:forEach items="${requestScope.complex.listOfUnits}" var="unit" varStatus="status">
                        <tr>
                            <td>${ status.count }</td>
                            <td>${ unit.exercise.title }</td>
                            <td>${ unit.exercise.adjusting }</td>
                            <td>${ unit.exercise.mistakes }</td>
                            <td><img src='<c:url value="${ unit.exercise.picturePath }"/>'
                                     class="center-block" width=200 alt="picture"/>
                            </td>
                            <td>
                                <audio controls style="width: 150px;">
                                    <source src='<c:url value="${ unit.exercise.audioPath }"/>' type="audio/mpeg">
                                </audio>
                            </td>
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
