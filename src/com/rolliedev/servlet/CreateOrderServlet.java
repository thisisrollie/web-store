package com.rolliedev.servlet;

import com.rolliedev.entity.PaymentMethod;
import com.rolliedev.util.JspHelper;
import com.rolliedev.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(UrlPath.ORDERS + "/create")
public class CreateOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("paymentMethods", PaymentMethod.values());
        req.getRequestDispatcher(JspHelper.getPath("create_order"))
                .forward(req, resp);
    }
}
