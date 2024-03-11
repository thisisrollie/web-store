package com.rolliedev.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class OrderDto {

    Long id;
    LocalDateTime date;
    String status;
    BigDecimal totalPrice;
    String deliverAddress;
    Long userId;
    List<CartItemDto> items;
}
