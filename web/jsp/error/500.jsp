<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<html>
<head>
    <title>
        <fmt:message key="jsp.500.page.title" bundle="${msg}"/>
    </title>
    <!-- Styles -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/error.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template center">
                <h1>
                    <fmt:message key="jsp.500.h1" bundle="${msg}"/>
                </h1>
                <h2>
                    <fmt:message key="jsp.500.h2" bundle="${msg}"/>
                </h2>
                <div class="error-details">
                    <fmt:message key="jsp.500.details" bundle="${msg}"/>
                </div><br />
                <div class="error-actions">
                    <a href="controller?executionCommand=REDIRECT" class="btn btn-primary btn-lg">
                        <span class="glyphicon glyphicon-home"></span>
                        <fmt:message key="jsp.500.button.home.value" bundle="${msg}"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
