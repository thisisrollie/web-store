package com.rolliedev.service;

import com.rolliedev.dao.UserDao;
import com.rolliedev.dto.UserDto;
import com.rolliedev.entity.User;
import com.rolliedev.exception.ValidationException;
import com.rolliedev.mapper.CreateUserMapper;
import com.rolliedev.mapper.UserMapper;
import com.rolliedev.validator.CreateUserValidator;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();

    public Optional<UserDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password)
                .map(userMapper::mapFrom); // user -> userDto
    }

    public Long create(UserDto userDto) {
        // 1. validation
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        // 2. mapping
        User user = createUserMapper.mapFrom(userDto);
        // 3. saving
        userDao.save(user);
        // 4. returning id
        return user.getId();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
