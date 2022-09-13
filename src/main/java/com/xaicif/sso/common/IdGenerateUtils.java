package com.xaicif.sso.common;

import java.util.Random;
import java.util.UUID;

public class IdGenerateUtils {

    private static Random random = new Random();

    public static String generateNumberId(String prefix, Integer length) {
        return prefix + randomNumber(length);
    }

    public static String randomNumber(int length) {
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sbf.append(random.nextInt(10));
        }
        return sbf.toString();
    }

    public static String randomLetter(int length) {
        StringBuffer sbf = new StringBuffer();
        char a;
        for (int i = 0; i < length; i++) {
            a = (char) (97 + random.nextInt(26));
            sbf.append(a);
        }
        return sbf.toString();
    }
}
