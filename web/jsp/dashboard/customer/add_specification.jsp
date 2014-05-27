<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<!DOCTYPE html>
<html>
<head>
    <title>New order</title>
    <!-- Styles -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/dashboard.css" rel="stylesheet">
</head>
<body>
    <script src="js/functions.js"></script>
    <!-- Header -->
    <c:import url="../common/header.jsp"/>

    <div class="container-fluid">
        <div class="row">
            <!-- Menu -->
            <c:set var="currentPage" value="order" scope="session"/>
            <c:import url="menu.jsp"/>
            <!-- Body -->
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <h2 class="sub-header">
                    <fmt:message key="jsp.customer.add.specification.body.title" bundle="${msg}"/>
                </h2>
                <!-- Form for creation specifications -->
                <div id="addForm">
                    <form id="order" method="get" action="controller">
                        <input type="hidden" name="executionCommand" value="CREATE_ORDER">
                        <input type="hidden" id="jobsCount" name="jobsCount" value="1">

                        <!-- Result message -->
                        <c:if test="${isFormCorrect eq 'true'}">
                            <div class="alert alert-success">
                                <fmt:message key="jsp.alert.order.form.correction.true" bundle="${msg}"/>
                            </div>
                        </c:if>
                        <c:if test="${isFormCorrect eq 'false'}">
                            <div class="alert alert-danger">
                                <fmt:message key="jsp.alert.order.form.correction.false" bundle="${msg}"/>
                            </div>
                        </c:if>

                        <input type="text" name="nameOfNewSpec" class="form-control"
                               placeholder="<fmt:message key="jsp.customer.placeholder.specification.name" bundle="${msg}"/>">
                        <div id="jobs" class="row" style="padding: 12px 0 0 0;">
                            <div class="col-xs-9">
                                <input id="job1" type="text" name="job1" class="form-control"
                                       placeholder="<fmt:message key="jsp.customer.placeholder.job.name" bundle="${msg}"/>">
                            </div>
                            <div class="col-xs-2">
                                <select id="qualification1" type="text" name="qualification1" class="form-control">
                                    <c:forEach var="qualification" items="${qualifications}" varStatus="status">
                                        <option>
                                            <c:out value="${qualification}"/>
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-xs-1">
                                <input id="count1" type="text" name="count1" class="form-control"
                                       placeholder="<fmt:message key="jsp.customer.placeholder.job.count" bundle="${msg}"/>">
                            </div>
                        </div>
                    </form>
                    <div style="padding-top: 12px;">
                        <button type="button" class="btn btn-default" onclick="addRequirement()">
                            <fmt:message key="jsp.customer.button.add.job" bundle="${msg}"/>
                        </button>
                        <input type="submit" form="order" class="btn btn-default" style="float: right;"
                               value="<fmt:message key="jsp.customer.button.make.order" bundle="${msg}"/> "/>
                    </div>
                </div>
                <!-- end form-->
            </div>
        </div>
    </div>
</body>
</html>