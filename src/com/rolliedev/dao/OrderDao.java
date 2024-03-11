package com.rolliedev.dao;

import com.rolliedev.entity.Item;
import com.rolliedev.entity.Order;
import com.rolliedev.util.ConnectionManager;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderDao implements Dao<Long, Order> {

    private static final OrderDao INSTANCE = new OrderDao();
    private static final String SAVE_ORDER_SQL = """
            INSERT INTO orders (date, status, total_price, deliver_address, user_id)
            VALUES (?, ?, ?, ?, ?);
            """;
    private static final String SAVE_ORDER_ITEMS_SQL = """
            INSERT INTO orders_items (order_id, item_id, price, quantity)
            VALUES (?, ?, ?, ?);
            """;

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public Order save(Order entity) {
        try (var connection = ConnectionManager.get();
             var orderPreparedStatement = connection.prepareStatement(SAVE_ORDER_SQL, RETURN_GENERATED_KEYS);
             var orderItemsPreparedStatement = connection.prepareStatement(SAVE_ORDER_ITEMS_SQL)) {
            orderPreparedStatement.setObject(1, entity.getDate());
            orderPreparedStatement.setObject(2, entity.getStatus().name());
            orderPreparedStatement.setObject(3, entity.getTotalPrice());
            orderPreparedStatement.setObject(4, entity.getDeliverAddress());
            orderPreparedStatement.setObject(5, entity.getUserId());

            orderPreparedStatement.executeUpdate();
            var generatedKeys = orderPreparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Long.class));
            saveOrderItems(entity, orderItemsPreparedStatement);

            return entity;
        }
    }

    private void saveOrderItems(Order order, PreparedStatement preparedStatement) throws SQLException {
        for (Item item : order.getItems()) {
            preparedStatement.setObject(1, order.getId());
            preparedStatement.setObject(2, item.getId());
            preparedStatement.setObject(3, item.getPrice());
            preparedStatement.setObject(4, item.getQuantity());

            preparedStatement.executeUpdate();
        }
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
