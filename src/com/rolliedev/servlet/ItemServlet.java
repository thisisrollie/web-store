package com.rolliedev.servlet;

import com.rolliedev.dto.ItemDto;
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
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new LinkedHashMap<ItemDto, Integer>());
        }
        var cart = (Map<ItemDto, Integer>) session.getAttribute("cart");
        session.setAttribute("cartSize", cart.values().stream()
                .mapToInt(Integer::intValue)
                .sum());
        var items = itemService.findAll();
        req.setAttribute("items", items);

        req.getRequestDispatcher(JspHelper.getPath("items"))
                .forward(req, resp);
    }
}
