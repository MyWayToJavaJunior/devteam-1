<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<html>
<head>
    <meta charset="utf-8">
    <title>
        <fmt:message key="jsp.manager.waiting.orders.page.tile" bundle="${msg}"/>
    </title>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles -->
    <link href="css/dashboard.css" rel="stylesheet">
</head>
<body>
    <!-- Header -->
    <c:import url="../common/header.jsp"/>
    <div class="container-fluid">
        <div class="row">
            <!-- Menu -->
            <c:set var="currentPage" value="orders" scope="session"/>
            <c:import url="menu.jsp"/>
            <!-- Body -->
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <h2 class="sub-header">
                    <fmt:message key="jsp.manager.waiting.orders.body.header" bundle="${msg}"/>
                </h2>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>№</th>
                            <th>
                                <fmt:message key="jsp.manager.waiting.orders.table.spec.name" bundle="${msg}"/>
                            </th>
                            <th>
                                <fmt:message key="jsp.manager.waiting.orders.table.jobs" bundle="${msg}"/>
                            </th>
                            <th>
                                <fmt:message key="jsp.manager.waiting.orders.table.customer" bundle="${msg}"/>
                            </th>
                            <th>
                                <fmt:message key="jsp.manager.waiting.orders.table.action" bundle="${msg}"/>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order" items="${ordersList}" varStatus="status">
                            <tr>
                                <td><c:out value="${status.count}"/></td>
                                <td>
                                    <c:out value="${order.name}"/>
                                </td>
                                <td><c:out value="${order.jobs}"/></td>
                                <td><c:out value="${order.email}"/></td>
                                <td>
                                    <a href="controller?executionCommand=PREPARE_PROJECT&orderId=<c:out value="${order.id}"/>">
                                        <fmt:message key="jsp.manager.waiting.orders.create.project" bundle="${msg}"/>
                                    </a>
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
