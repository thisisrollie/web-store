package com.rolliedev.dao;

import com.rolliedev.entity.Order;
import com.rolliedev.util.ConnectionManager;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderDao implements Dao<Long, Order> {

    private static final OrderDao INSTANCE = new OrderDao();
    private static final String SAVE_SQL = """
            INSERT INTO orders(date, status, total_price, deliver_address, user_id)
            VALUES (?, ?, ?, ?, ?);
            """;

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void update(Order entity) {

    }

    @SneakyThrows
    @Override
    public Order save(Order entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getDate());
            preparedStatement.setObject(2, entity.getStatus().name());
            preparedStatement.setObject(3, entity.getTotalPrice());
            preparedStatement.setObject(4, entity.getDeliverAddress());
            preparedStatement.setObject(5, entity.getUserId());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Long.class));

            return entity;
        }
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
