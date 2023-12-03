package com.rolliedev.servlet;

import com.rolliedev.service.ItemService;
import com.rolliedev.util.JspHelper;
import com.rolliedev.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(UrlPath.ITEMS)
public class ItemServlet extends HttpServlet {

    private final ItemService itemService = ItemService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        var cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart != null) {
            session.setAttribute("cartSize", cart.values().stream().mapToInt(Integer::intValue).sum());
        }
        var items = itemService.findAll();
        req.setAttribute("items", items);

        req.getRequestDispatcher(JspHelper.getPath("items"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        var cart = (Map<String, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
        }
        cart.merge(req.getParameter("itemId"), 1, Integer::sum);
        doGet(req, resp);
    }
}
