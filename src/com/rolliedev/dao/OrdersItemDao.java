package com.rolliedev.dao;

import com.rolliedev.entity.OrdersItemId;
import com.rolliedev.entity.OrdersItems;
import com.rolliedev.util.ConnectionManager;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrdersItemDao implements Dao<OrdersItemId, OrdersItems> {

    private static final OrdersItemDao INSTANCE = new OrdersItemDao();
    private static final String FIND_ALL_SQL = """
            SELECT order_id, item_id, price, quantity
            FROM orders_items;
            """;
    private static final String SAVE_SQL = """
            INSERT INTO orders_items (order_id, item_id, price, quantity)
            VALUES (?, ?, ?, ?);
            """;

    @SneakyThrows
    @Override
    public List<OrdersItems> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<OrdersItems> ordersItemsList = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ordersItemsList.add(buildEntity(resultSet));
            }
            return ordersItemsList;
        }
    }

    @Override
    public Optional<OrdersItems> findById(OrdersItemId id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(OrdersItemId id) {
        return false;
    }

    @Override
    public void update(OrdersItems entity) {

    }

    @SneakyThrows
    @Override
    public OrdersItems save(OrdersItems entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setObject(1, entity.getOrderId());
            preparedStatement.setObject(2, entity.getItemId());
            preparedStatement.setObject(3, entity.getPrice());
            preparedStatement.setObject(4, entity.getQuantity());

            preparedStatement.executeUpdate();
            return entity;
        }
    }

    private OrdersItems buildEntity(ResultSet resultSet) throws SQLException {
        return OrdersItems.builder()
                .orderId(resultSet.getObject("order_id", Long.class))
                .itemId(resultSet.getObject("item_id", Long.class))
                .price(resultSet.getObject("price", BigDecimal.class))
                .quantity(resultSet.getObject("quantity", Integer.class))
                .build();
    }

    public static OrdersItemDao getInstance() {
        return INSTANCE;
    }
}
