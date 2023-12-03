package com.rolliedev.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CartItemDto {

    Long id;
    String name;
    String image;
    BigDecimal price;
    Integer quantity;
    Integer maxQuantity;
    String description;
}
