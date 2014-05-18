<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<html>
<head>
    <title>
        <fmt:message key="jsp.manager.details.page.title" bundle="${msg}"/>
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
                    <fmt:message key="jsp.manager.details.body.header" bundle="${msg}"/>
                    <c:out value="${projectName}"/>
                </h2>
                <div class="table-responsive">

                    <!-- Labels block -->
                    <div class="row">
                        <div class="col-xs-1">
                            <label>№</label>
                        </div>
                        <div class="col-xs-5">
                            <label>
                                <fmt:message key="jsp.manager.details.label.job" bundle="${msg}"/>
                            </label>
                        </div>
                        <div class="col-xs-4">
                            <label>
                                <fmt:message key="jsp.manager.details.label.employee" bundle="${msg}"/>
                            </label>
                        </div>
                        <div class="col-xs-2">
                            <label>
                                <fmt:message key="jsp.manager.details.label.time" bundle="${msg}"/>
                            </label>
                        </div>
                    </div>
                    <hr>

                    <c:forEach var="job" items="${jobsList}" varStatus="status">
                        <div class="row">
                            <div class="col-xs-1">
                                <p>
                                    <c:out value="${status.count}"/>
                                </p>
                            </div>
                            <div class="col-xs-5">
                                <p>
                                    <c:out value="${job.name}"/>
                                </p>
                            </div>
                            <c:set var="mandt" value="${jobsMap[job.id]}" scope="page"/>
                            <c:set var="keys" value="${mandt.keySet()}" scope="page"/>
                            <div class="col-xs-4">
                                <c:forEach var="key" items="${keys}">
                                    <p>
                                        <c:out value="${key}"/>
                                    </p>
                                </c:forEach>
                            </div>
                            <div class="col-xs-2">
                                <c:forEach var="key" items="${keys}">
                                    <p>
                                        <c:out value="${mandt[key]}"/>
                                    </p>
                                </c:forEach>
                            </div>
                        </div>
                        <hr>
                    </c:forEach>

                    <div>
                        <form>
                            <input type="hidden" name="executionCommand" value="CLOSE_PROJECT">
                            <input type="hidden" name="projectId" value="${projectObj.id}">
                            <input type="submit" class="btn btn-default btn-right"
                                   value="<fmt:message key="jsp.manager.details.button.close.project" bundle="${msg}"/>"/>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </div>
</body>
</html>
