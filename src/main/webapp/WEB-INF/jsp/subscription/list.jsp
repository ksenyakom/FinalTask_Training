<%--
  User: User
  Date: 14.01.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="${cookie.language.value}">
<%--проверить это--%>
<head>
    <title><fmt:message key="title.subscription_list"/></title>
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
            <h4 class="text-center"><fmt:message key="table.subscription_list.name"/></h4>
            <c:import url="../common/messages.jsp"/>

            <br>
            <br>
            <form action="list.html" method="GET">
                <div class="form-group">
                    <label class="control-label col-sm-offset-2 col-sm-2" for="action"><fmt:message
                            key="label.show"/></label>
                    <div class="col-sm-6 col-md-4">
                        <select id="action" class="form-control" name="action">
                            <option value="active" <c:if test='${action == "active"}'> selected</c:if> ><fmt:message
                                    key="dropdown.active"/></option>
                            <option value="all" <c:if test='${action == "all"}'> selected</c:if> ><fmt:message
                                    key="dropdown.all"/></option>
                        </select>
                    </div>
                </div>
                <input type="submit" value='<fmt:message key="button.show"/>'/></td>
            </form>

            <br>
            <form onsubmit="return validateDelete(this)" enctype="multipart/form-data">
                <input type="hidden" name="action" value="${action}">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <caption>

                        </caption>
                        <tr class="active">
                            <th scope="col">№</th>
                            <th scope="col"><fmt:message key="table.login"/></th>
                            <th scope="col"><fmt:message key="table.begin_date"/></th>
                            <th scope="col"><fmt:message key="table.end_date"/></th>
                            <th scope="col"><fmt:message key="table.price"/></th>
                            <th scope="col"><fmt:message key="table.edit"/></th>
                            <th scope="col"><fmt:message key="table.remove"/></th>
                        </tr>
                        <c:forEach items="${lst}" var="subscription" varStatus="status">
                            <tr>
                                <td>${ status.count }</td>
                                <td>${ subscription.visitor.login }</td>
                                <td><ctg:parse localDate="${ subscription.beginDate }"
                                               language="${cookie.language.value}"/></td>
                                <td><ctg:parse localDate="${ subscription.endDate }"
                                               language="${cookie.language.value}"/></td>
                                <td><fmt:formatNumber value="${ subscription.price }"/></td>
                                <td><c:url value="/subscription/edit.html" var="myUrl">
                                    <c:param name="editId" value="${subscription.id}"/>
                                    <c:param name="action" value="${action}"/>
                                </c:url>
                                    <a href='${myUrl}'><fmt:message key="table.edit"/></a></td>

                                <td><input type="checkbox" class="require-one" name="remove"
                                           value="${subscription.id}"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <button type="submit" class="btn btn-warning" formmethod="post" formaction="delete.html">
                        <fmt:message key="table.remove"/></button>
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
