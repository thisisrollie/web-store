package com.rolliedev.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
public class OrderDto {

    Long id;
    LocalDate date;
    String status;
    BigDecimal totalPrice;
    String deliverAddress;
    Long userId;
    List<CartItemDto> items;
}
