<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<html>
<head>
    <title>
        <fmt:message key="jsp.employee.menu.title" bundle="${msg}"/>
    </title>
</head>
<body>
    <div class="col-sm-3 col-md-2 sidebar">
        <ul class="nav nav-sidebar">
            <c:choose>
                <c:when test="${sessionScope.currentPage eq 'job'}">
                    <li class="active">
                </c:when>
                <c:otherwise>
                    <li>
                </c:otherwise>
            </c:choose>
                <a href="controller?executionCommand=SHOW_JOB">
                    <fmt:message key="jsp.employee.menu.current.job" bundle="${msg}"/>
                </a>
            </li>
        </ul>
    </div>
</body>
</html>
