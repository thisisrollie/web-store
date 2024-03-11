package com.rolliedev.dao;

import com.rolliedev.entity.Payment;
import com.rolliedev.util.ConnectionManager;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class PaymentDao implements Dao<Long, Payment> {

    private static final PaymentDao INSTANCE = new PaymentDao();
    private static final String SAVE_SQL = """
            INSERT INTO payment(date, status, payment_method, order_id)
            VALUES (?, ?, ?, ?);
            """;

    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public Payment save(Payment entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getDate());
            preparedStatement.setObject(2, entity.getStatus().name());
            preparedStatement.setObject(3, entity.getPaymentMethod().name());
            preparedStatement.setObject(4, entity.getOrderId());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Long.class));

            return entity;
        }
    }

    public static PaymentDao getInstance() {
        return INSTANCE;
    }
}
