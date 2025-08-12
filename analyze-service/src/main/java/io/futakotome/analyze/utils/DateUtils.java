package io.futakotome.analyze.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public final class DateUtils {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATE_TIME_WITH_MILLISECOND_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static List<String> splitDateByDay(LocalDate start, LocalDate end) {
        long days = start.until(end, ChronoUnit.DAYS);
        List<String> dates = new ArrayList<>();
        for (int i = 0; i <= days; i++) {
            dates.add(start.plusDays(i).format(DATE_FORMATTER));
        }
        return dates;
    }
}
