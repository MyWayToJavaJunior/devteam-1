<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Language</title>
</head>
<body>
    <form id="lang" method="post" action="controller">
        <input type="hidden" name="executionCommand" value="CHANGE_LANGUAGE">
        <select name="choice" class="lang-block" onchange="this.form.submit()" >
            <option>
                <c:out value="${sessionScope.user.language}"/>
            </option>
            <c:if test="${sessionScope.user.language eq 'en'}">
                <option>ru</option>
            </c:if>
            <c:if test="${sessionScope.user.language eq 'ru'}">
                <option>en</option>
            </c:if>
        </select>
    </form>
</body>
</html>
