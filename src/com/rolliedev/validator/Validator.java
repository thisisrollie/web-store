package com.rolliedev.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
