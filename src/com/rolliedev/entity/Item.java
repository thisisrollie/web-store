package com.rolliedev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class Item {

    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private Integer quantity;
    private String description;
}
