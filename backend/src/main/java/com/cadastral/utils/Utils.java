package com.cadastral.utils;

import java.util.Base64;

public class Utils {

    public static String mountToken(String username, String senha) {
        String token = String.format("%s:%s", username, decode(senha));
        return Base64.getEncoder().encodeToString(token.getBytes());
    }

    public static String decode(String value) {
        if (value == null) {
            return "";
        }
        return new String(Base64.getDecoder().decode(value.getBytes()));
    }

    public static String encode(String value) {
        if (value == null) {
            return "";
        }
        return new String(Base64.getEncoder().encode(value.getBytes()));
    }

    public static boolean isUniqueViolation(String exceptionMessage) {
        return exceptionMessage != null && exceptionMessage.contains("Unique index or primary key violation");
    }
}
