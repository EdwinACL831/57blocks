package com.example.hw57blocks.utils;

import java.util.regex.Pattern;

public class EmailUtil {
    private static final String EMAIL_REGEX = "^([\\d\\w\\S\\.-_]+)@([\\d\\w\\S\\.-_]+)\\.(\\w){2,5}$";
    private static final String PASSWORD_REGEX = "([[a-z][A-Z](!|#|\\?|@|\\])]+){10,}";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX,Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX,Pattern.CASE_INSENSITIVE);
        return pattern.matcher(password).matches();
    }
}
