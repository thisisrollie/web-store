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
        var cart = (Map<String, Integer>) session.getAttribute("cart");

        List<CartItemDto> cartItems = new ArrayList<>();
        if (cart != null) {
            cart.forEach((itemId, itemQuantity) -> itemService.findById(Long.valueOf(itemId)).ifPresent(itemDto -> {
                var cartItem = createCartItem(itemDto, itemQuantity);
                cartItems.add(cartItem);
            }));
        }
        session.setAttribute("cartItems", cartItems);
        // TODO: 11/12/2023 save totalPrice in session scope?
        session.setAttribute("totalPrice", BigDecimal.valueOf(cartItems.stream()
                .map(cartItem -> cartItem.getPrice().doubleValue() * cartItem.getQuantity())
                .mapToDouble(Double::doubleValue)
                .sum()).setScale(2, RoundingMode.HALF_UP));

        req.getRequestDispatcher(JspHelper.getPath("shopping_cart"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        var cart = (Map<String, Integer>) session.getAttribute("cart");
        updateCart(req, cart);
        session.setAttribute("cartSize", cart.values().stream().mapToInt(Integer::intValue).sum());

        doGet(req, resp);
    }

    private CartItemDto createCartItem(ItemDto itemDto, Integer cartItemQuantity) {
        return CartItemDto.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .image(itemDto.getImage())
                .price(itemDto.getPrice())
                .quantity(cartItemQuantity)
                .maxQuantity(itemDto.getQuantity())
                .description(itemDto.getDescription())
                .build();
    }

    private void updateCart(HttpServletRequest req, Map<String, Integer> cart) {
        cart.forEach((k, v) -> {
            String paramValue;
            // TODO: 11/12/2023 change the logic? (maybe)
            if ((paramValue = req.getParameter("item-" + k)) != null) {
                if (!v.equals(Integer.valueOf(paramValue))) {
                    cart.replace(k, Integer.valueOf(paramValue));
                }
            }
        });
    }
}
