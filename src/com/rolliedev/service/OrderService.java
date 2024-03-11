package com.rolliedev.service;

import com.rolliedev.dao.OrderDao;
import com.rolliedev.dto.OrderDto;
import com.rolliedev.entity.Order;
import com.rolliedev.mapper.CreateOrderMapper;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderService {

    private static final OrderService INSTANCE = new OrderService();

    private final OrderDao orderDao = OrderDao.getInstance();
    private final CreateOrderMapper createOrderMapper = CreateOrderMapper.getInstance();

    public Long create(OrderDto orderDto) {
        Order order = createOrderMapper.mapFrom(orderDto);
        orderDao.save(order);
        return order.getId();
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
