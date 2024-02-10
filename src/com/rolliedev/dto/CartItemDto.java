package com.rolliedev.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CartItemDto {

    ItemDto item;
    Integer quantity;

    public double getTotalPrice() {
        return item.getPrice().doubleValue() * quantity;
    }
}
