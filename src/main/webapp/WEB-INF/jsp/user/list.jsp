<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.user.list"/></title>
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
                <c:if test="${not empty warningMessage}">
                    <p class="text-danger"><fmt:message key="${warningMessage}"/></p></c:if>
                <c:if test="${not empty successMessage}">
                    <p class="text-success"><fmt:message key="${successMessage}"/></p></c:if>

            <h4 class="text-center"><fmt:message key="title.user.list"/></h4>
            <br>
            <br>
            <form action="list.html" method="GET">
                <div class="form-group">
                    <label class="control-label col-sm-offset-2 col-sm-2" for="role"><fmt:message
                            key="label.show"/></label>
                    <div class="col-sm-6 col-md-4">
                        <select id="role" class="form-control" name="role">
                            <option value="Visitor"><fmt:message key="label.user_list.visitors"/></option>
                            <option value="Trainer"><fmt:message key="label.user_list.trainers"/></option>
                        </select>
                    </div>
                </div>
                <input type="submit" value='<fmt:message key="button.show"/>'/>
            </form>

            <br>
            <form onsubmit="return validateDelete(this)">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="table.login"/></th>
                            <th scope="col"><fmt:message key="table.email"/></th>
                            <th scope="col"><fmt:message key="table.role"/></th>
                            <th scope="col"><fmt:message key="table.remove"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="user" varStatus="status">
                            <tr>
                                <td>${ status.count }</td>
                                <td>${ user.login }</td>
                                <td>${ user.email}</td>
                                <td>${ user.role }</td>
                                <td><input id="remove" class="require-one" type="checkbox" name="remove" value="${user.id}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <button type="submit" class="btn btn-warning" formaction="deleteUser.html" formmethod="post"
                            name="role" value="${param.get("role")}" ><fmt:message key="table.remove"/></button>
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
