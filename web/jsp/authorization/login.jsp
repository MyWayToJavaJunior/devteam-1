<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="by.bsu.mmf.devteam.resource.Resource" var="msg"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="jsp.login.page.title" bundle="${msg}"/> </title>
    <!-- Bootstrap styles -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles -->
    <link href="css/login.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <!--  Login form  -->
    <form class="form-signin" role="form" method="post" action="controller">
        <h2 class="form-signin-heading"><fmt:message key="jsp.login.form.header" bundle="${msg}"/></h2>
        <input type="hidden" name="executionCommand" value="LOGIN">
        <input type="email" name="email" class="form-control"
               placeholder="<fmt:message key="jsp.login.form.email.placeholder" bundle="${msg}"/>" required autofocus>
        <input type="password" name="password" class="form-control"
               placeholder="<fmt:message key="jsp.login.form.password.placeholder" bundle="${msg}"/>" required>
        <label class="text-danger">
            ${errorLoginPasswordMessage}
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
            <fmt:message key="jsp.login.form.submit.value" bundle="${msg}"/>
        </button>
    </form>
</div>
</body>
</html>
