package com.rolliedev.mapper;

import com.rolliedev.dto.ItemDto;
import com.rolliedev.entity.Item;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateItemMapper implements Mapper<ItemDto, Item> {

    private static final CreateItemMapper INSTANCE = new CreateItemMapper();

    @Override
    public Item mapFrom(ItemDto object) {
        return Item.builder()
                .name(object.getName())
                .image(object.getImage())
                .price(object.getPrice())
                .quantity(object.getQuantity())
                .description(object.getDescription())
                .build();
    }

    public static CreateItemMapper getInstance() {
        return INSTANCE;
    }
}
