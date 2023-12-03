package com.rolliedev.service;

import com.rolliedev.dao.OrdersItemDao;
import com.rolliedev.dto.OrdersItemsDto;
import com.rolliedev.mapper.CreateOrdersItemMapper;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrdersItemService {

    private static final OrdersItemService INSTANCE = new OrdersItemService();

    private final OrdersItemDao ordersItemDao = OrdersItemDao.getInstance();
    private final CreateOrdersItemMapper createOrdersItemMapper = CreateOrdersItemMapper.getInstance();

    public void create(OrdersItemsDto ordersItemsDto) {
        var ordersItems = createOrdersItemMapper.mapFrom(ordersItemsDto);
        ordersItemDao.save(ordersItems);
    }

    public static OrdersItemService getInstance() {
        return INSTANCE;
    }
}
