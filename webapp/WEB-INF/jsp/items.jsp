<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="${pageContext.request.contextPath}/cart">Cart(${sessionScope.cartSize})</a>

    <c:forEach var="item" items="${requestScope.items}">
        <div>
            <img height="300"
                 width="300"
                 src="${pageContext.request.contextPath}/images/${item.image}"
                 alt="${item.name}"><br>
            <div>
                <form action="${pageContext.request.contextPath}/cart" method="post">
                    <input type="hidden" name="itemId" value="${item.id}">
                    <span>${item.name}</span><br>
                    <span>Price: €${item.price}</span>
                    <button type="submit">Add to cart</button>
                </form>
            </div>
            <div>
                <span>Description:</span><br>
                <span>${item.description}</span>
            </div>
        </div>
    </c:forEach>
</body>
</html>
