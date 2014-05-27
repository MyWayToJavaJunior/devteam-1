<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<html>
<head>
    <title>
        <fmt:message key="jsp.manager.bills.page.title" bundle="${msg}"/>
    </title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/dashboard.css" rel="stylesheet">
</head>
<body>
    <!-- Header -->
    <c:import url="../common/header.jsp"/>

    <div class="container-fluid">
        <div class="row">
            <!-- Menu -->
            <c:set var="currentPage" value="bills" scope="session"/>
            <c:import url="menu.jsp"/>
            <!-- Body -->
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <h2 class="sub-header">
                    <fmt:message key="jsp.manager.bills.body.header" bundle="${msg}"/>
                </h2>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>№</th>
                            <th>
                                <fmt:message key="jsp.manager.bills.table.name" bundle="${msg}"/>
                            </th>
                            <th>
                                <fmt:message key="jsp.manager.bills.table.cost" bundle="${msg}"/>
                            </th>
                            <th>
                                <fmt:message key="jsp.manager.bills.table.project" bundle="${msg}"/>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="bill" items="${billsList}" varStatus="status">
                            <tr>
                                <td><c:out value="${status.count}"/></td>
                                <td><c:out value="${bill.name}"/></td>
                                <td><c:out value="${bill.sum}"/></td>
                                <td><c:out value="${namesMap[bill.id]}"/></td>
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
