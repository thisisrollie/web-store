package com.rolliedev.servlet;

import com.rolliedev.dto.ItemDto;
import com.rolliedev.service.ShoppingCartService;
import com.rolliedev.util.JspHelper;
import com.rolliedev.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(UrlPath.CART)
public class ShoppingCartServlet extends HttpServlet {

    private final ShoppingCartService cartService = ShoppingCartService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        Map<ItemDto, Integer> cart = (Map<ItemDto, Integer>) session.getAttribute("cart");
        var cartItems = cartService.getCartItems(cart);
        var totalPrice = cartService.calculateTotalPrice(cartItems);
        session.setAttribute("cartItems", cartItems);
        session.setAttribute("totalPrice", totalPrice);

        req.getRequestDispatcher(JspHelper.getPath("shopping_cart"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<ItemDto, Integer> cart = (Map<ItemDto, Integer>) req.getSession().getAttribute("cart");
        var parameterMap = req.getParameterMap();
        cartService.updateCart(cart, parameterMap);
        if (parameterMap.keySet().size() == 1) {
            resp.sendRedirect(UrlPath.ITEMS);
            return;
        }
        doGet(req, resp);
    }
}
