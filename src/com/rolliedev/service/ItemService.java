package com.rolliedev.service;

import com.rolliedev.dao.ItemDao;
import com.rolliedev.dto.ItemDto;
import com.rolliedev.mapper.ItemMapper;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ItemService {

    private static final ItemService INSTANCE = new ItemService();

    private final ItemDao itemDao = ItemDao.getInstance();
    private final ItemMapper itemMapper = ItemMapper.getInstance();

    public List<ItemDto> findAll() {
        var items = itemDao.findAll();
        return items.stream()
                .map(itemMapper::mapFrom)
                .toList();
    }

    public static ItemService getInstance() {
        return INSTANCE;
    }
}
