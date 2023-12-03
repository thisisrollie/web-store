package com.rolliedev.service;

import com.rolliedev.dao.UserDao;
import com.rolliedev.dto.UserDto;
import com.rolliedev.exception.ValidationException;
import com.rolliedev.mapper.CreateUserMapper;
import com.rolliedev.mapper.UserMapper;
import com.rolliedev.validator.CreateUserValidator;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final UserDao userDao = UserDao.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    public Optional<UserDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password)
                .map(userMapper::mapFrom);
    }

    public Long create(UserDto userDto) {
        // validation
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        // map
        var userEntity = createUserMapper.mapFrom(userDto);
        // save
        userDao.save(userEntity);
        // return id
        return userEntity.getId();
    }

    public List<UserDto> findAll() {
        return userDao.findAll().stream()
                .map(userMapper::mapFrom)
                .collect(toList());
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
