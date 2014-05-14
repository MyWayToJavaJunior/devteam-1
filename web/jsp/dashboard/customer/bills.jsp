<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<!DOCTYPE html>
<html>
<head>
    <title>
        <fmt:message key="jsp.customer.bills.page.title" bundle="${msg}"/>
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
            <c:set var="currentPage" value="bills" scope="session"/>
            <c:import url="menu.jsp"/>
            <!-- Body -->
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <h2 class="sub-header">
                    <fmt:message key="jsp.customer.bills.body.header" bundle="${msg}"/>
                </h2>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>№</th>
                            <th>
                                <fmt:message key="jsp.customer.bills.table.invoice" bundle="${msg}"/>
                            </th>
                            <th>
                                <fmt:message key="jsp.customer.bills.table.cost" bundle="${msg}"/>
                            </th>
                            <th>
                                <fmt:message key="jsp.customer.bills.table.action" bundle="${msg}"/>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="customerBill" items="${billsList}" varStatus="status">
                            <tr>
                                <td><c:out value="${status.count}"/></td>
                                <td><c:out value="${customerBill.name}"/></td>
                                <td><c:out value="${customerBill.sum}"/></td>
                                <td>
                                    <button type="button" class="btn btn-warning">
                                        <fmt:message key="jsp.customer.bills.button.pay" bundle="${msg}"/>
                                    </button>
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