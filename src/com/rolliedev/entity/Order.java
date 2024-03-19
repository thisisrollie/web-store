package com.rolliedev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Order {

    private Long id;
    private LocalDate date;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private String deliverAddress;
    private Long userId;
    private List<Item> items;
}
