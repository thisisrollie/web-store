<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <ul>
        <c:if test="${not empty sessionScope.cartSize}">
            <a href="${pageContext.request.contextPath}/cart">Cart(${sessionScope.cartSize})</a>
        </c:if>
        <c:forEach var="item" items="${requestScope.items}">
            <div>
                <img height="300" width="300" src="${pageContext.request.contextPath}/images/${item.image}" alt="${item.name}">
                <div>
                    <span>Description:</span>
                    <p>${item.description}</p>
                </div>
                <div>
                    <form action="${pageContext.request.contextPath}/items" method="post">
                        <input type="hidden" name="itemId" value="${item.id}">
                        <span>Price: €${item.price}</span>
                        <button type="submit">Add to cart</button>
                    </form>
                </div>
                <hr>
            </div>
        </c:forEach>
    </ul>
</body>
</html>
