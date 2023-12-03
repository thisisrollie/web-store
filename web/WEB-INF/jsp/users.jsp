<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:forEach var="user" items="${requestScope.users}">
        <span>${user.firstName} - ${user.lastName} - ${user.email} - ${user.role} - ${user.gender}</span><br>
    </c:forEach>
</body>
</html>
