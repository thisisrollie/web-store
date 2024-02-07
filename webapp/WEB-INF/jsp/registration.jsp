<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Registration</h1>
    <form action="${pageContext.request.contextPath}/registration" method="post">
        <label for="firstNameId">Firstname:
            <input type="text" id="firstNameId" name="firstName">
        </label><br>
        <label for="lastNameId">Lastname:
            <input type="text" id="lastNameId" name="lastName">
        </label><br>
        <label for="birthdayId">Birthday:
            <input type="date" id="birthdayId" name="birthday">
        </label><br>
        <label for="emailId">Email:
            <input type="text" id="emailId" name="email">
        </label><br>
        <label for="passwordId">Password:
            <input type="password" id="passwordId" name="password">
        </label><br>
        <label for="roleId">Role:
            <select name="role" id="roleId">
                <c:forEach var="role" items="${requestScope.roles}">
                    <option value="${role}">${role}</option>
                </c:forEach>
            </select>
        </label><br>
        <label for="genderId">Gender:
            <select name="gender" id="genderId">
                <c:forEach var="gender" items="${requestScope.genders}">
                    <option value="${gender}">${gender}</option>
                </c:forEach>
            </select>
        </label><br>
        <button type="submit">Send</button>

        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <span>${error.message}</span><br>
                </c:forEach>
            </div>
        </c:if>
    </form>
</body>
</html>
