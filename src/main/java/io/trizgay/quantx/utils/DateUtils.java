package io.trizgay.quantx.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateUtils {
    public static LocalDateTime getDateTimeOfTimeStamp(Long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8));
    }

    public static LocalDateTime getDateTimePattern(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.parse(date, formatter);
    }
}
