<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<!DOCTYPE html>
<html>
<head>
    <title>
        <fmt:message key="jsp.manager.projects.page.title" bundle="${msg}"/>
    </title>
    <!-- Styles -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/dashboard.css" rel="stylesheet">
</head>
<body>
    <!-- Header -->
    <c:import url="../common/header.jsp"/>

    <div class="container-fluid">
        <div class="row">
            <!-- Menu -->
            <c:set var="currentPage" value="projects" scope="session"/>
            <c:import url="menu.jsp"/>
            <!-- Body -->
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <h2 class="sub-header">
                    <fmt:message key="jsp.manager.projects.body.header" bundle="${msg}"/>
                </h2>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>№</th>
                            <th>
                                <fmt:message key="jsp.manager.projects.table.name" bundle="${msg}"/>
                            </th>
                            <th>
                                <fmt:message key="jsp.manager.projects.table.status" bundle="${msg}"/>
                            </th>
                            <th>
                                <fmt:message key="jsp.manager.projects.table.time" bundle="${msg}"/>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="project" items="${projectList}" varStatus="status">
                            <tr>
                                <td><c:out value="${status.count}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${project.status eq 'processing'}">
                                            <a href="controller?executionCommand=SHOW_PROJECT&projectId=<c:out value="${project.id}"/>">
                                                <c:out value="${project.name}"/>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${project.name}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:out value="${project.status}"/>
                                </td>
                                <td>
                                    <c:out value="${project.time}"/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
