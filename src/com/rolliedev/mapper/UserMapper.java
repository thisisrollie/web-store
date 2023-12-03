package com.rolliedev.mapper;

import com.rolliedev.dto.UserDto;
import com.rolliedev.entity.User;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper<User, UserDto> {

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(User object) {
        return UserDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .birthday(object.getBirthday().toString())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(object.getRole().name())
                .gender(object.getGender().name())
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
