package com.rolliedev.validator;

import com.rolliedev.dto.UserDto;
import com.rolliedev.entity.Gender;
import com.rolliedev.util.EmailFormatter;
import com.rolliedev.util.LocalDateFormatter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<UserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(UserDto object) {
        ValidationResult validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(object.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "The birthday is invalid"));
        }
        if (!EmailFormatter.isValid(object.getEmail())) {
            validationResult.add(Error.of("invalid.email", "The email is invalid. Should be in format: some_text@mail_server.domain"));
        }
        if (Gender.find(object.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "The gender is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
