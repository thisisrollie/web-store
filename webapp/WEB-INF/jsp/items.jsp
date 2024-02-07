<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:forEach var="item" items="${requestScope.items}">
        <div>
            <div>
                <span>Description:</span>
                <p>${item.description}</p>
            </div>
        </div>
    </c:forEach>
</body>
</html>
