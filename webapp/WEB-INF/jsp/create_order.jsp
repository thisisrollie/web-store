<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/orders" method="post">
        <label for="deliveryAddressId">Address for delivery:
            <input id="deliveryAddressId" type="text" name="deliveryAddress" value="${param.deliveryAddress}" required>
        </label><br>
        <label for="paymentMethodId">Payment method:
            <select name="paymentMethod" id="paymentMethodId">
                <c:forEach var="paymentMethod" items="${requestScope.paymentMethods}">
                    <option value=${paymentMethod}>${paymentMethod}</option>
                </c:forEach>
            </select>
        </label><br>
        <hr>
        <h1>Order review</h1>
        <c:forEach var="cartItem" items="${sessionScope.cartItems}">
            <div>
                <img height="300"
                     width="300"
                     src="${pageContext.request.contextPath}/images/${cartItem.item.image}"
                     alt="${cartItem.item.name}">
            </div>
            <div>
                <span>Quantity: ${cartItem.quantity}</span>
                <span>Price: €${cartItem.item.price}</span>
            </div>
            <hr>
        </c:forEach>
        <span>Total price: €${sessionScope.totalPrice}</span>
        <button type="submit">Place Order</button>
    </form>
</body>
</html>
