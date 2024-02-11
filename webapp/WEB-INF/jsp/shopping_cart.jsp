<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:choose>
        <c:when test="${empty sessionScope.cart}">
            <h1>You do not have any items in your cart yet</h1>
            <p>Please continue <a href="${pageContext.request.contextPath}/items">to the main page</a></p>
        </c:when>
        <c:otherwise>
            <h1>Shopping cart:</h1>

            <form action="${pageContext.request.contextPath}/cart" method="post">
                <c:forEach var="cartItem" items="${requestScope.cartItems}">
                    <div>
                        <img height="300"
                             width="300"
                             src="${pageContext.request.contextPath}/images/${cartItem.item.image}"
                             alt="${cartItem.item.name}">
                    </div>
                    <div>
                        <label>Quantity:
                            <input type="hidden" name="itemId" value="${cartItem.item.id}">
                            <input type="number"
                                   name="QItem-${cartItem.item.id}"
                                   value="${cartItem.quantity}"
                                   min="0"
                                   max="${cartItem.item.quantity}"
                            >
                        </label>
                        <span>Price: €${cartItem.item.price}</span>
                    </div>
                    <hr>
                </c:forEach>
                <span>Total price: €${requestScope.totalPrice}</span>
                <button type="submit">Save</button><br>
                <a href="${pageContext.request.contextPath}/items">Back to items</a>
            </form>
        </c:otherwise>
    </c:choose>
</body>
</html>
