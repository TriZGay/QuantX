package io.futakotome.rtck.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class AddTimeUtil {
    public static String generateAddTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
