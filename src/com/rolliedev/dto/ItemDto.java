package com.rolliedev.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ItemDto {

    Long id;
    String name;
    String image;
    BigDecimal price;
    Integer quantity;
    String description;
}
