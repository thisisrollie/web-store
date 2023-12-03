<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>

<%--    <script>--%>
<%--        function updateQuantity(itemId) {--%>
<%--            let req = new XMLHttpRequest();--%>
<%--            req.open('POST', '/cart', true);--%>
<%--            req.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');--%>

<%--            let currentQuantity = document.getElementsByName('item-' + itemId)[0].valueAsNumber;--%>
<%--            let data = 'item-' + itemId + '=' + currentQuantity;--%>
<%--            req.send(data);--%>
<%--        }--%>
<%--    </script>--%>
</head>
<body>
    <c:choose>
        <c:when test="${empty sessionScope.cartItems}">
            <div>
                <h1>You do not have any items in your cart yet</h1>
                <p>Please continue <a href="${pageContext.request.contextPath}/items">to the main page</a></p>
            </div>
        </c:when>
        <c:otherwise>
            <h1>Shopping cart:</h1>
            <form action="${pageContext.request.contextPath}/cart" method="post">
                <c:forEach var="cartItem" items="${sessionScope.cartItems}">
                    <div>
                        <img height="300"
                             width="300"
                             src="${pageContext.request.contextPath}/images/${cartItem.image}"
                             alt="${cartItem.name}">
                    </div>
                    <div>
                        <label>Quantity:
                            <input type="number"
                                   name="item-${cartItem.id}"
                                   value="${cartItem.quantity}"
                                   min="1"
                                   max="${cartItem.maxQuantity}"
<%--                                   onchange="updateQuantity(${cartItem.id})"--%>
                            >
                        </label>
                        <span>Price: €${cartItem.price}</span>
                    </div>
                    <hr>
                </c:forEach>
                <span>Total price: €${sessionScope.totalPrice}</span>
                <button type="submit">Save</button>
                <a href="${pageContext.request.contextPath}/items">
                    <button type="button">Back to items</button>
                </a>
                <a href="${pageContext.request.contextPath}/transport-payment">
                    <button type="button">Proceed to Checkout</button>
                </a>
            </form>
        </c:otherwise>
    </c:choose>
</body>
</html>