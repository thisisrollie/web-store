package com.rolliedev.dao;

import com.rolliedev.entity.Item;
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
public class ItemDao implements Dao<Long, Item> {

    private static final ItemDao INSTANCE = new ItemDao();
    private static final String FIND_ALL_SQL = """
            SELECT id, name, image, price, quantity, description
            FROM item;
            """;
    private static final String FIND_BY_ID_SQL = """
            SELECT id, name, image, price, quantity, description
            FROM item
            WHERE id = ?;
            """;

    @SneakyThrows
    @Override
    public List<Item> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                items.add(buildEntity(resultSet));
            }
            return items;
        }
    }

    @SneakyThrows
    @Override
    public Optional<Item> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, id);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildEntity(resultSet));
            }
            return Optional.empty();
        }
    }

    @Override
    public Item save(Item entity) {
        return null;
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
