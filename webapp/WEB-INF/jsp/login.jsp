<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Login</h1>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="emailId">Email:
            <input type="text" id="emailId" name="email" value="${param.email}">
        </label><br>
        <label for="passwordId">Password:
            <input type="password" id="passwordId" name="password">
        </label><br>
        <button type="submit">Login</button>

        <c:if test="${param.error != null}">
            <div style="color: red">
                <span>Email or password is not correct</span>
            </div>
        </c:if>
    </form>
</body>
</html>
