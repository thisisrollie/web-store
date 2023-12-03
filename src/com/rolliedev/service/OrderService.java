package com.rolliedev.service;

import com.rolliedev.dao.OrderDao;
import com.rolliedev.dto.OrderDto;
import com.rolliedev.mapper.CreateOrderMapper;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderService {

    private static final OrderService INSTANCE = new OrderService();

    private final OrderDao orderDao = OrderDao.getInstance();
    private final CreateOrderMapper createOrderMapper = CreateOrderMapper.getInstance();

    public Long create(OrderDto orderDto) {
        var orderEntity = createOrderMapper.mapFrom(orderDto);
        orderDao.save(orderEntity);
        return orderEntity.getId();
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
