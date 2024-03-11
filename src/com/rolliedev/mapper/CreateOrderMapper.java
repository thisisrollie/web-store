package com.rolliedev.mapper;

import com.rolliedev.dto.CartItemDto;
import com.rolliedev.dto.OrderDto;
import com.rolliedev.entity.Item;
import com.rolliedev.entity.Order;
import com.rolliedev.entity.OrderStatus;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateOrderMapper implements Mapper<OrderDto, Order> {

    private static final CreateOrderMapper INSTANCE = new CreateOrderMapper();

    @Override
    public Order mapFrom(OrderDto object) {
        return Order.builder()
                .date(object.getDate())
                .status(OrderStatus.valueOf(object.getStatus()))
                .totalPrice(object.getTotalPrice())
                .deliverAddress(object.getDeliverAddress())
                .userId(object.getUserId())
                .items(object.getItems().stream().map(this::buildCartItemEntity).toList())
                .build();
    }

    private Item buildCartItemEntity(CartItemDto cartItem) {
        return Item.builder()
                .id(cartItem.getItem().getId())
                .name(cartItem.getItem().getName())
                .image(cartItem.getItem().getImage())
                .price(cartItem.getItem().getPrice())
                .description(cartItem.getItem().getDescription())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public static CreateOrderMapper getInstance() {
        return INSTANCE;
    }
}
