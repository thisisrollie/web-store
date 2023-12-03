package com.rolliedev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private Integer quantity;
    private String description;
}
