package com.rolliedev.mapper;

import com.rolliedev.dto.OrdersItemsDto;
import com.rolliedev.entity.OrdersItems;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateOrdersItemMapper implements Mapper<OrdersItemsDto, OrdersItems> {

    private static final CreateOrdersItemMapper INSTANCE = new CreateOrdersItemMapper();

    @Override
    public OrdersItems mapFrom(OrdersItemsDto object) {
        return OrdersItems.builder()
                .orderId(object.getOrderId())
                .itemId(object.getItemId())
                .price(object.getPrice())
                .quantity(object.getQuantity())
                .build();
    }

    public static CreateOrdersItemMapper getInstance() {
        return INSTANCE;
    }
}
