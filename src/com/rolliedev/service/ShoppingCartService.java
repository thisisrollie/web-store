package com.rolliedev.service;

import com.rolliedev.dto.CartItemDto;
import com.rolliedev.dto.ItemDto;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ShoppingCartService {

    private static final ShoppingCartService INSTANCE = new ShoppingCartService();

    private final ItemService itemService = ItemService.getInstance();

    public void updateCart(Map<ItemDto, Integer> cart, Map<String, String[]> parameterMap) {
        var itemIds = parameterMap.get("itemId");
        for (String itemId : itemIds) {
            var item = itemService.findById(Long.valueOf(itemId)).orElse(null);
            if (item != null) {
                var quantityParam = parameterMap.get("QItem-" + itemId);
                if (quantityParam != null) {
                    var updatedQuantity = Integer.parseInt(quantityParam[0]);
                    if (updatedQuantity == 0) {
                        cart.remove(item);
                    } else {
                        cart.put(item, updatedQuantity);
                    }
                } else {
                    cart.merge(item, 1, Integer::sum);
                }
            }
        }
    }

    public BigDecimal calculateTotalPrice(List<CartItemDto> cartItems) {
        return BigDecimal.valueOf(cartItems.stream()
                .mapToDouble(CartItemDto::getTotalPrice)
                .sum()
        ).setScale(2, RoundingMode.HALF_UP);
    }

    public List<CartItemDto> getCartItems(Map<ItemDto, Integer> cart) {
        List<CartItemDto> cartItems = new ArrayList<>();
        cart.forEach((key, value) -> {
            CartItemDto cartItem = buildCartItem(key, value);
            cartItems.add(cartItem);
        });
        return cartItems;
    }

    private CartItemDto buildCartItem(ItemDto itemDto, int quantity) {
        return CartItemDto.builder()
                .item(itemDto)
                .quantity(quantity)
                .build();
    }

    public static ShoppingCartService getInstance() {
        return INSTANCE;
    }
}
