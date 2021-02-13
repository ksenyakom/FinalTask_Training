<%--
  Author: Ksiniya Oznobishina 
  Date: 08.02.2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie.language.value}"/>
<fmt:setBundle basename="properties.text"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title><fmt:message key="title.journal"/></title>
    <%@ include file="common/head.jsp" %>

</head>
<body>
<c:import url="common/main_menu.jsp"/>

<div class="container-fluid text-center">
    <div class="row content">
        <%--side menu of the page--%>
        <c:import url="/WEB-INF/jsp/common/side_menu.jsp"/>
        <%--Content of the page --%>
        <div class="col-sm-8 text-justify">
            <br>
            <h5>Наши контакты: </h5><br>
            <h5> Ксения Ознобишина </h5>
            <h5> email: ksenyakom@gmail.com </h5>
            <h5> тел: +375 29 703 54 11 </h5>
        </div>
        <div class="col-sm-2 sidenav">
            <c:import url="common/ads.jsp"/>
        </div>
    </div>
</div>

<c:import url="common/footer.jsp"/>
<br>
</body>
</html>
