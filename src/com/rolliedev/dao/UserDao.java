package com.rolliedev.dao;

import com.rolliedev.entity.Gender;
import com.rolliedev.entity.Role;
import com.rolliedev.entity.User;
import com.rolliedev.util.ConnectionManager;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserDao implements Dao<Long, User> {

    private static final UserDao INSTANCE = new UserDao();
    private static final String SAVE_SQL = """
            INSERT INTO users (first_name, last_name, birthday, email, password, role, gender)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;
    private static final String FIND_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT id, first_name, last_name, birthday, email, password, role, gender
            FROM users
            WHERE email = ? AND password = ?;
            """;

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD_SQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            var resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    @Override
    public List<User> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @SneakyThrows
    @Override
    public User save(User entity) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getFirstName());
            preparedStatement.setObject(2, entity.getLastName());
            preparedStatement.setObject(3, entity.getBirthday());
            preparedStatement.setObject(4, entity.getEmail());
            preparedStatement.setObject(5, entity.getPassword());
            preparedStatement.setObject(6, entity.getRole().name());
            preparedStatement.setObject(7, entity.getGender().name());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Long.class));

            return entity;
        }
    }

    private User buildEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getObject("id", Long.class))
                .firstName(resultSet.getObject("first_name", String.class))
                .firstName(resultSet.getObject("last_name", String.class))
                .birthday(resultSet.getObject("birthday", Date.class).toLocalDate())
                .email(resultSet.getObject("email", String.class))
                .password(resultSet.getObject("password", String.class))
                .role(Role.valueOf(resultSet.getObject("role", String.class)))
                .gender(Gender.valueOf(resultSet.getObject("gender", String.class)))
                .build();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
