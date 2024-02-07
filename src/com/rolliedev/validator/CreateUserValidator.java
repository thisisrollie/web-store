package com.rolliedev.validator;

import com.rolliedev.dto.UserDto;
import com.rolliedev.util.EmailChecker;
import com.rolliedev.util.LocalDateFormatter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<UserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    public ValidationResult isValid(UserDto object) {
        ValidationResult validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(object.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "The birthday is invalid"));
        }
        if (!EmailChecker.isValid(object.getEmail())) {
            validationResult.add(Error.of("invalid.email", "The email is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
