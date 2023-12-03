package com.rolliedev.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class OrdersItemsDto {

    Long orderId;
    Long itemId;
    BigDecimal price;
    Integer quantity;
}
