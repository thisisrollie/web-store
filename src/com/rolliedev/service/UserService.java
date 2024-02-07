package com.rolliedev.service;

import com.rolliedev.dao.UserDao;
import com.rolliedev.dto.UserDto;
import com.rolliedev.entity.Gender;
import com.rolliedev.entity.Role;
import com.rolliedev.entity.User;
import com.rolliedev.exception.ValidationException;
import com.rolliedev.util.LocalDateFormatter;
import com.rolliedev.validator.CreateUserValidator;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    public Long create(UserDto userDto) {
        // 1. validation
        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        // 2. mapping
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .birthday(LocalDateFormatter.format(userDto.getBirthday()))
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(Role.valueOf(userDto.getRole()))
                .gender(Gender.valueOf(userDto.getGender()))
                .build();
        // 3. saving
        userDao.save(user);
        // 4. returning id
        return user.getId();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
