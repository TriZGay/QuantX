package io.futakotome.trade.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class DatetimeUtil {
    public static String convertDatetimeWithTimeZone(String datetimeWithTimeZone) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(datetimeWithTimeZone, formatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        return zonedDateTime.format(outputFormatter);
    }
}
