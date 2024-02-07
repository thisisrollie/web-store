package com.rolliedev.mapper;

import com.rolliedev.dto.ItemDto;
import com.rolliedev.entity.Item;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ItemMapper implements Mapper<Item, ItemDto> {

    private static final ItemMapper INSTANCE = new ItemMapper();

    @Override
    public ItemDto mapFrom(Item object) {
        return ItemDto.builder()
                .id(object.getId())
                .name(object.getName())
                .image(object.getImage())
                .quantity(object.getQuantity())
                .price(object.getPrice())
                .description(object.getDescription())
                .build();
    }

    public static ItemMapper getInstance() {
        return INSTANCE;
    }
}
