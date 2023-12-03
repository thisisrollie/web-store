package com.rolliedev.dto;

import com.rolliedev.entity.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class OrderDto {

    Long id;
    LocalDateTime date;
    OrderStatus orderStatus;
    BigDecimal totalPrice;
    String deliverAddress;
    Long userId;
}
