package io.futakotome.quantx.collect.utils;

public final class StringUtils {
    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isNotEmpty(Integer integer) {
        return integer != null;
    }

    public static boolean isNotEmpty(Double d) {
        return d != null;
    }

    public static boolean isNotEmpty(Long l) {
        return l != null;
    }
}