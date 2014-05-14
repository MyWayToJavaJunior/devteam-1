<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<html>
<head>
    <title>
        <fmt:message key="jsp.employee.finished.page.title" bundle="${msg}"/>
    </title>
    <!-- Styles -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/dashboard.css" rel="stylesheet">
</head>
<body>

<c:import url="../common/header.jsp"/>

<div class="container-fluid">
    <div class="row">
        <!-- Menu -->
        <c:set var="currentPage" value="jobs" scope="session"/>
        <c:import url="menu.jsp"/>

        <!-- Body -->
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h2 class="sub-header">
                <fmt:message key="jsp.employee.finished.body.header" bundle="${msg}"/>
            </h2>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>№</th>
                        <th>
                            <fmt:message key="jsp.employee.finished.table.job" bundle="${msg}"/>
                        </th>
                        <th>
                            <fmt:message key="jsp.employee.finished.table.manager" bundle="${msg}"/>
                        </th>
                        <th>
                            <fmt:message key="jsp.employee.finished.table.time" bundle="${msg}"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>Design</td>
                        <td>manager1@devteam.com</td>
                        <td>32</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
