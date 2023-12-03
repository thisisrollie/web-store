package com.rolliedev.servlet;

import com.rolliedev.dto.CartItemDto;
import com.rolliedev.dto.OrderDto;
import com.rolliedev.dto.OrdersItemsDto;
import com.rolliedev.entity.OrderStatus;
import com.rolliedev.entity.PaymentMethod;
import com.rolliedev.service.OrderService;
import com.rolliedev.service.OrdersItemService;
import com.rolliedev.util.JspHelper;
import com.rolliedev.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(UrlPath.TRANSPORT_PAYMENT)
public class TransportPaymentServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();
    private final OrdersItemService ordersItemService = OrdersItemService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("paymentMethods", List.of(PaymentMethod.values()));

        req.getRequestDispatcher(JspHelper.getPath("transport-payment"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("cardNumber") == null && req.getParameter("paymentMethod").equals(PaymentMethod.BY_CARD.name())) {
            req.setAttribute("byCard", "");
            doGet(req, resp);
            return;
        }
        createOrder(req);
        resp.sendRedirect(UrlPath.ITEMS);
    }

    private void createOrder(HttpServletRequest req) {
        var session = req.getSession();
        var orderDto = OrderDto.builder()
                .date(LocalDateTime.now())
                .orderStatus(OrderStatus.IN_PROCESS)
                .totalPrice((BigDecimal) session.getAttribute("totalPrice"))
                .deliverAddress(req.getParameter("deliveryAddress"))
                // TODO: 12/3/2023 add user to session
                .userId(2L)
//                .userId(((UserDto) session.getAttribute("user")).getId())
                .build();

        var orderId = orderService.create(orderDto);
        List<CartItemDto> cartItems = (List<CartItemDto>) session.getAttribute("cartItems");
        cartItems.forEach(cartItem -> ordersItemService.create(OrdersItemsDto.builder()
                .orderId(orderId)
                .itemId(cartItem.getId())
                .price(cartItem.getPrice())
                .quantity(cartItem.getQuantity())
                .build()));

        session.removeAttribute("cart");
        session.removeAttribute("cartItems");
        session.removeAttribute("totalPrice");
        session.removeAttribute("cartSize");
    }

    private void createPayment() {
        
    }
}
