package com.rolliedev.exception;

import com.rolliedev.validator.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class ValidationException extends RuntimeException {

    @Getter
    private List<Error> errors;
}
