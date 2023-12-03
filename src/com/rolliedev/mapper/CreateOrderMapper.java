package com.rolliedev.mapper;

import com.rolliedev.dto.OrderDto;
import com.rolliedev.entity.Order;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateOrderMapper implements Mapper<OrderDto, Order> {

    private static final CreateOrderMapper INSTANCE = new CreateOrderMapper();

    @Override
    public Order mapFrom(OrderDto object) {
        return Order.builder()
                .date(object.getDate())
                .status(object.getOrderStatus())
                .totalPrice(object.getTotalPrice())
                .deliverAddress(object.getDeliverAddress())
                .userId(object.getUserId())
                .build();
    }

    public static CreateOrderMapper getInstance() {
        return INSTANCE;
    }
}
