package com.rolliedev.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class EmailFormatter {

    private static final String REGEX = "[a-zA-Z]\\w*@\\w{3,}\\.[a-z]{2,3}";

    public static boolean isValid(String email) {
        return email != null && Pattern.matches(REGEX, email);
    }
}
