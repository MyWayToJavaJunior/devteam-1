<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<html>
<head>
    <title></title>
</head>
<body>
    <div class="col-sm-3 col-md-2 sidebar">
        <ul class="nav nav-sidebar">
            <c:choose>
                <c:when test="${sessionScope.currentPage eq 'orders'}">
                    <li class="active">
                </c:when>
                        <c:otherwise>
                            <li>
                        </c:otherwise>
            </c:choose>
                <a href="controller?executionCommand=WAITING_ORDERS">
                    <fmt:message key="jsp.manager.menu.orders" bundle="${msg}"/>
                </a>
            </li>
        </ul>
        <ul class="nav nav-sidebar">
            <c:choose>
                <c:when test="${sessionScope.currentPage eq 'projects'}">
                    <li class="active">
                </c:when>
                        <c:otherwise>
                            <li>
                        </c:otherwise>
            </c:choose>
                <a href="controller?executionCommand=MANAGED_PROJECTS">
                    <fmt:message key="jsp.manager.menu.projects" bundle="${msg}"/>
                </a>
            </li>
            <c:choose>
                <c:when test="${sessionScope.currentPage eq 'bills'}">
                    <li class="active">
                </c:when>
                        <c:otherwise>
                            <li>
                        </c:otherwise>
            </c:choose>
                <a href="controller?executionCommand=MANAGED_BILLS">
                    <fmt:message key="jsp.manager.menu.bills" bundle="${msg}"/>
                </a>
            </li>
        </ul>
    </div>
</body>
</html>
