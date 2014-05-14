<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<html>
<head>
    <title>Header</title>
</head>
<body>
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">
                    <fmt:message key="jsp.customer.dashboard.type" bundle="${msg}"/>
                </a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <c:import url="../common/language.jsp"/>
                    </li>
                    <li>
                        <a href="controller?executionCommand=LOGOUT">
                            <fmt:message key="jsp.common.sign.out" bundle="${msg}"/>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>
