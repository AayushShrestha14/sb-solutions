package com.sb.solutions.core.utils.string;

import java.security.SecureRandom;

public class StringUtil {

    private static final String seed = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static SecureRandom random = new SecureRandom();

    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(seed.charAt(random.nextInt(seed.length())));
        }
        return sb.toString();
    }

}
