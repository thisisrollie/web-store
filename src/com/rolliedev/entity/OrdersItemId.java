package com.rolliedev.entity;

import lombok.Value;

@Value(staticConstructor = "of")
public class OrdersItemId {

    Long orderId;
    Long itemId;
}
