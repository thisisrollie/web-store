package com.rolliedev.service;

import com.rolliedev.dao.ItemDao;
import com.rolliedev.dto.ItemDto;
import com.rolliedev.mapper.CreateItemMapper;
import com.rolliedev.mapper.ItemMapper;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ItemService {

    private static final ItemService INSTANCE = new ItemService();

    private final ItemDao itemDao = ItemDao.getInstance();
    private final ItemMapper itemMapper = ItemMapper.getInstance();
    private final CreateItemMapper createItemMapper = CreateItemMapper.getInstance();

    public List<ItemDto> findAll() {
        return itemDao.findAll().stream()
                .map(itemMapper::mapFrom)
                .collect(toList());
    }

    public Optional<ItemDto> findById(Long id) {
        return itemDao.findById(id)
                .map(itemMapper::mapFrom);
    }

    public Long create(ItemDto item) {
        // TODO: 11/4/2023 validation

        // map
        var itemEntity = createItemMapper.mapFrom(item);
        //save
        itemDao.save(itemEntity);
        // return id
        return itemEntity.getId();
    }

    public static ItemService getInstance() {
        return INSTANCE;
    }
}
