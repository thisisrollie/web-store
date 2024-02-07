package com.rolliedev.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

    Long id;
    String firstName;
    String lastName;
    String birthday;
    String email;
    String password;
    String role;
    String gender;
}
