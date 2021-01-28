<%--
  Author: Ksiniya Oznobishina 
  Date: 23.01.2021
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
<c:set var="isAdmin"
       value="${sessionScope.authorizedUser.role.name().equals('ADMINISTRATOR')}"/>
<c:import url="/WEB-INF/jsp/common/main_menu.jsp"/>

<div class="container-fluid text-center">
    <div class="row content">
        <%--side menu of the page--%>
        <c:choose>
            <c:when test="${isAdmin}">
                <c:import url="/WEB-INF/jsp/admin/side_menu.jsp"/>
            </c:when>
            <c:otherwise>
                <c:import url="/WEB-INF/jsp/trainer/side_menu.jsp"/>
            </c:otherwise>
        </c:choose>
        <%--Content of the page --%>
        <div class="col-sm-10 text-justify">
            <h2><fmt:message key="title.exercise_list"/> ПРИВЕЕЕЕЕТТТ</h2>
            жывлашщ
            <c:set var="number" value="${ (currentPage -1) * recordsPerPage }" scope="page"/>
            <p>Номер  ${number} </p>

            <c:if test="${not empty warningMessage}">
                <p class="text-danger"><fmt:message key="${warningMessage}"/></p>
            </c:if>
            <c:if test="${not empty successMessage}">
                <p class="text-success"><fmt:message key="${successMessage}"/></p>
            </c:if>


            <form onsubmit="return validateDelete(this)">
                <div class="table-responsive">
                    <c:if test="${isAdmin}">
                        <a href='<c:url value="add.html"/>' class="btn btn-success" role="button"><fmt:message
                                key="button.add"/></a>
                    </c:if>
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

                            <c:if test="${isAdmin}">
                                <th scope="col"><fmt:message key="table.edit"/></th>
                                <th scope="col"><fmt:message key="table.remove"/></th>
                            </c:if>
                        </tr>

                        <c:forEach items="${lst}" var="exercise" varStatus="status">
                            <tr>
                                <td>${ number + status.count }</td>
                                <td>${ exercise.title }</td>
                                <td>${ exercise.adjusting }</td>
                                <td>${ exercise.mistakes }</td>
                                <td><img src='<c:url value="${ exercise.picturePath }"/>'
                                         class="img-responsive center-block" width=200 alt="picture"/></td>
                                <td>
                                    <audio controls style="width: 150px;">
                                        <source src='<c:url value="${ exercise.audioPath }"/>' type="audio/mpeg">
                                    </audio>
                                </td>
                                <c:if test="${isAdmin}">
                                    <td><a href='<c:url value="edit.html?exerciseId=${exercise.id}"/>'>
                                        <fmt:message key="table.edit"/></a></td>
                                    <td><input type="checkbox" class="require-one" name="remove"
                                               value="${exercise.id}"/></td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </table>
                    <c:if test="${isAdmin}">
                        <button type="submit" class="btn btn-warning" formmethod="post" formaction="delete.html">
                            <fmt:message key="table.remove"/></button>
                    </c:if>
                </div>
            </form>



            <nav aria-label="Navigation for countries">
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item"><a class="page-link"
                                                 href="list.html?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}">Previous</a>
                        </li>
                    </c:if>

                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <li class="page-item active"><a class="page-link">
                                        ${i} <span class="sr-only">(current)</span></a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item"><a class="page-link"
                                                         href="list.html?recordsPerPage=${recordsPerPage}&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item"><a class="page-link"
                                                 href="list.html?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>

    </div>
</div>


<br>
<c:import url="/WEB-INF/jsp/common/footer.jsp"/>

</body>
</html>
