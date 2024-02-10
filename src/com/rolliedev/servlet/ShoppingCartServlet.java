package com.rolliedev.servlet;

import com.rolliedev.dto.CartItemDto;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(UrlPath.CART)
public class ShoppingCartServlet extends HttpServlet {

    private final ItemService itemService = ItemService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        var cart = (Map<ItemDto, Integer>) session.getAttribute("cart");
        List<CartItemDto> cartItems = new ArrayList<>();
        cart.forEach((key, value) -> {
            CartItemDto cartItem = buildCartItem(key, value);
            cartItems.add(cartItem);
        });
        req.setAttribute("cartItems", cartItems);
        // TODO: 2/9/2024 maybe save totalPrice in session scope ???
        req.setAttribute("totalPrice", BigDecimal.valueOf(cartItems.stream()
                .mapToDouble(CartItemDto::getTotalPrice)
                .sum()).setScale(2, RoundingMode.HALF_UP));

        req.getRequestDispatcher(JspHelper.getPath("shopping_cart"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        var cart = (Map<ItemDto, Integer>) session.getAttribute("cart");

        var parameterMap = req.getParameterMap();
        var itemIds = parameterMap.get("itemId");
        for (String itemId : itemIds) {
            var item = itemService.findById(Long.valueOf(itemId));
            if (item.isPresent()) {
                var quantityParam = parameterMap.get("item-" + itemId);
                if (quantityParam != null) {
                    cart.put(item.get(), Integer.parseInt(quantityParam[0]));
                } else {
                    cart.merge(item.get(), 1, Integer::sum);
                }
            }
        }
        if (parameterMap.keySet().size() == 1) {
            resp.sendRedirect(UrlPath.ITEMS);
            return;
        }
        doGet(req, resp);
    }

    private CartItemDto buildCartItem(ItemDto itemDto, int quantity) {
        return CartItemDto.builder()
                .item(itemDto)
                .quantity(quantity)
                .build();
    }
}
