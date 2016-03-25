package com.github.achatain.nopasswordauthentication.utils;

public class MsgResources {

    private static final String BLANK_PARAMETER = "Parameter [%s] should not be blank";
    private static final String INVALID_EMAIL = "Email address [%s] is invalid";

    private MsgResources() {
    }

    public static String paramShouldNotBeBlank(String parameter) {
        return String.format(BLANK_PARAMETER, parameter);
    }

    public static String invalidEmail(String email) {
        return String.format(INVALID_EMAIL, email);
    }
}
