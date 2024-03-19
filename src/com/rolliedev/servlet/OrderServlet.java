package com.rolliedev.servlet;

import com.rolliedev.dto.CartItemDto;
import com.rolliedev.dto.OrderDto;
import com.rolliedev.dto.PaymentDto;
import com.rolliedev.dto.UserDto;
import com.rolliedev.entity.OrderStatus;
import com.rolliedev.entity.PaymentStatus;
import com.rolliedev.service.OrderService;
import com.rolliedev.service.PaymentService;
import com.rolliedev.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebServlet(UrlPath.ORDERS)
public class OrderServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();
    private final PaymentService paymentService = PaymentService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processOrder(req);
        resp.sendRedirect("/temp.html");
    }

    private void processOrder(HttpServletRequest req) {
        var orderId = createOrder(req);
        createPayment(req, orderId);
    }

    private Long createOrder(HttpServletRequest req) {
        var session = req.getSession();
        var cartItems = (List<CartItemDto>) session.getAttribute("cartItems");
        var totalPrice = (BigDecimal) session.getAttribute("totalPrice");
        var orderDto = OrderDto.builder()
                .date(LocalDate.now())
                .status(OrderStatus.IN_PROCESS.name())
                .totalPrice(totalPrice)
                .deliverAddress(req.getParameter("deliveryAddress"))
                .userId(((UserDto) session.getAttribute("user")).getId())
                .items(cartItems)
                .build();
        return orderService.create(orderDto);
    }

    private void createPayment(HttpServletRequest req, Long orderId) {
        PaymentDto paymentDto = PaymentDto.builder()
                .date(LocalDate.now())
                .status(PaymentStatus.PROCESSING)
                .paymentMethod(req.getParameter("paymentMethod"))
                .orderId(orderId)
                .build();
        paymentService.create(paymentDto);
    }
}
