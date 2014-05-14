<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.user.language}"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<html>
<head>
    <title>
        <fmt:message key="jsp.manager.create.project.page.title" bundle="${msg}"/>
    </title>
    <!-- Styles -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/dashboard.css" rel="stylesheet">
</head>
<body>
<!-- Header -->
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <p class="navbar-brand">
                <fmt:message key="jsp.manager.dashboard.type" bundle="${msg}"/>
            </p>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <!-- Body -->
        <div class="col-sm-12">
            <form id="cancel" method="post" action="controller">
                <input type="hidden" name="executionCommand" value="SKIP_PREPARING">
                <input type="hidden" name="specId" value="<c:out value="${specId}"/>">
            </form>
            <h2 class="sub-header">
                <fmt:message key="jsp.manager.create.project.body.header" bundle="${msg}"/>
                <c:out value="${specName}"/>
            </h2>
            <div class="table-responsive">

                <div class="row">
                    <div class="col-xs-1">
                        <label>№</label>
                    </div>
                    <div class="col-xs-5">
                        <label>
                            <fmt:message key="jsp.manager.create.project.label.job" bundle="${msg}"/>
                        </label>
                    </div>
                    <div class="col-xs-1">
                        <label>
                            <fmt:message key="jsp.manager.create.project.label.required" bundle="${msg}"/>
                        </label>
                    </div>
                    <div class="col-xs-1">
                        <label>
                            <fmt:message key="jsp.manager.create.project.label.qualification" bundle="${msg}"/>
                        </label>
                    </div>
                    <div class="col-xs-3">
                        <label>
                            <fmt:message key="jsp.manager.create.project.label.free" bundle="${msg}"/>
                        </label>
                    </div>
                    <div class="col-xs-1">
                        <label>
                            <fmt:message key="jsp.manager.create.project.label.cost" bundle="${msg}"/>
                        </label>
                    </div>
                </div>
                <hr>

                <form id="create_project" method="post" action="controller">
                    <input type="hidden" name="executionCommand" value="CREATE_PROJECT"/>
                    <input type="hidden" name="specId" value="<c:out value="${specId}"/>"/>

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
                        <div class="col-xs-1">
                            <p>
                                <c:out value="${job.specialist}"/>
                            </p>
                        </div>
                        <div class="col-xs-1">
                            <p>
                                <c:out value="${job.qualification}"/>
                            </p>
                        </div>
                        <div class="col-xs-3">
                            <select multiple class="form-control" name="employees<c:out value="${job.id}"/>">
                                <c:forEach var="list" items="${mailsMap[job.id]}">
                                    <option>
                                        <c:out value="${list}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-xs-1">
                            <input type="text" class="form-control" name="cost<c:out value="${job.id}"/>" placeholder="$">
                        </div>
                    </div>
                    <hr>
                </c:forEach>

                <input type="text" name="nameOfNewSpec" class="form-control"
                       placeholder="<fmt:message key="jsp.manager.create.project.spec.name.ph" bundle="${msg}"/>">
                <hr>
                </form>

                <div>
                    <input form="cancel" type="submit" class="btn btn-default"
                           value="<fmt:message key="jsp.manager.create.project.button.cancel" bundle="${msg}"/>">
                    <input form="create_project" type="submit" class="btn btn-default btn-right"
                           value="<fmt:message key="jsp.manager.create.project.button.create" bundle="${msg}"/>">
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>
