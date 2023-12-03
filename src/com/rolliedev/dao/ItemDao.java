package com.rolliedev.dao;

import com.rolliedev.entity.Item;
import com.rolliedev.util.ConnectionManager;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.*;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class ItemDao implements Dao<Long, Item> {

    private static final ItemDao INSTANCE = new ItemDao();
    private static final String FIND_ALL_SQL = """
            SELECT id, name, image, price, quantity, description
            FROM item;
            """;
    private static final String SAVE_SQL = """
            INSERT INTO item(name, image, price, quantity, description)
            VALUES (?, ?, ?, ?, ?);
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, name, image, price, quantity, description
            FROM item
            WHERE item.id = ?;
            """;

    @Override
    @SneakyThrows
    public List<Item> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Item> items = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                items.add(buildEntity(resultSet));
            }
            return items;
        }
    }

    @Override
    @SneakyThrows
    public Optional<Item> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, id);

            var resultSet = preparedStatement.executeQuery();
            Item item = null;
            if (resultSet.next()) {
                item = buildEntity(resultSet);
            }
            return Optional.ofNullable(item);
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void update(Item entity) {

    }

    @Override
    @SneakyThrows
    public Item save(Item entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getImage());
            preparedStatement.setObject(3, entity.getPrice());
            preparedStatement.setObject(4, entity.getQuantity());
            preparedStatement.setObject(5, entity.getDescription());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Long.class));

            return entity;
        }
    }

    private Item buildEntity(ResultSet resultSet) throws SQLException {
        return Item.builder()
                .id(resultSet.getObject("id", Long.class))
                .name(resultSet.getObject("name", String.class))
                .image(resultSet.getObject("image", String.class))
                .price(resultSet.getObject("price", BigDecimal.class))
                .quantity(resultSet.getObject("quantity", Integer.class))
                .description(resultSet.getObject("description", String.class))
                .build();
    }

    public static ItemDao getInstance() {
        return INSTANCE;
    }
}
