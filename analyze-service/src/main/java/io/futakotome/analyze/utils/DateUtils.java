package io.futakotome.analyze.utils;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public static String hkTradeDateStart(String someDay) {
        LocalDate oneDay = LocalDate.parse(someDay, DATE_FORMATTER);
        return LocalDateTime.of(oneDay, LocalTime.of(9, 30)).format(DATE_TIME_FORMATTER);
    }

    public static String hkTradeDateEnd(String someDay) {
        LocalDate oneDay = LocalDate.parse(someDay, DATE_FORMATTER);
        return LocalDateTime.of(oneDay, LocalTime.of(16, 0)).format(DATE_TIME_FORMATTER);
    }
}
