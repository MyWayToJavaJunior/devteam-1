<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<html>
<head>
    <title>
        <fmt:message key="jsp.employee.current.page.title" bundle="${msg}"/>
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
        <c:set var="currentPage" value="job" scope="session"/>
        <c:import url="menu.jsp"/>

        <!-- Body -->
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <c:choose>
                <c:when test="${isFree eq 'false'}">
                    <h2 class="sub-header">
                        <fmt:message key="jsp.employee.current.body.header" bundle="${msg}"/>
                        You are free now.
                    </h2>
                </c:when>
                <c:otherwise>
                    <h2 class="sub-header">
                        <fmt:message key="jsp.employee.current.body.header" bundle="${msg}"/>
                        <c:out value="${currentProject.name}"/>
                    </h2>
                    <div class="table-responsive">
                        <c:if test="${result eq 'true'}">
                            <div class="alert alert-success">
                                <fmt:message key="jsp.employee.message.time.saved" bundle="${msg}"/>
                            </div>
                        </c:if>
                        <c:if test="${result eq 'false'}">
                            <div class="alert alert-danger">
                                <fmt:message key="jsp.employee.message.incorrect.time" bundle="${msg}"/>
                            </div>
                        </c:if>
                        <div class="row">
                            <div class="col-xs-4">
                                <label>
                                    <fmt:message key="jsp.employee.label.job.name" bundle="${msg}"/>
                                </label>
                            </div>
                            <div class="col-xs-3">
                                <label>
                                    <fmt:message key="jsp.employee.label.manager" bundle="${msg}"/>
                                </label>
                            </div>
                            <div class="col-xs-1">
                                <label>
                                    <fmt:message key="jsp.employee.label.time" bundle="${msg}"/>
                                </label>
                            </div>
                            <div class="col-xs-2">
                                <label>
                                    <fmt:message key="jsp.employee.label.new.time" bundle="${msg}"/>
                                </label>
                            </div>
                            <div class="col-xs-2">
                                <label>
                                    <fmt:message key="jsp.employee.label.action.time" bundle="${msg}"/>
                                </label>
                            </div>
                        </div>
                        <hr>

                        <div class="row">
                            <div class="col-xs-4">
                                <p>
                                    <c:out value="${employeeJob.name}"/>
                                </p>
                            </div>
                            <div class="col-xs-3">
                                <p>
                                    <c:out value="${managerMail}"/>
                                </p>
                            </div>
                            <div class="col-xs-1">
                                <p>
                                    <c:out value="${employeeJob.time}"/>
                                </p>
                            </div>
                            <div class="col-xs-2">
                                <form id="newTimeForm" method="post" action="controller">
                                    <input type="hidden" name="executionCommand" value="SET_TIME">
                                    <input type="hidden" name="jobId" value="<c:out value="${employeeJob.id}"/>">
                                    <input type="text" class="form-control" name="newTime"
                                           placeholder="<fmt:message key="jsp.employee.new.time.placeholder"
                                           bundle="${msg}"/>">
                                </form>
                            </div>
                            <div class="col-xs-2">
                                <input type="submit" form="newTimeForm" class="btn btn-default"
                                       value="<fmt:message key="jsp.employee.label.button.value" bundle="${msg}"/>">
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>
</body>
</html>
