package com.sb.solutions.core.utils.string;

import java.security.SecureRandom;

public class StringUtil {

    private static final String COMBO_SEED = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBER_SEED = "0123456789";

    private static SecureRandom random = new SecureRandom();

    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(COMBO_SEED.charAt(random.nextInt(COMBO_SEED.length())));
        }
        return sb.toString();
    }

    public static String generateNumber(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(NUMBER_SEED.charAt(random.nextInt(NUMBER_SEED.length())));
        }
        return sb.toString();
    }

}
