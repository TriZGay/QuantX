package io.futakotome.analyze.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void splitDateByDay() {
        List<String> dates = DateUtils.splitDateByDay(LocalDate.parse("2015-11-11", DateUtils.DATE_FORMATTER),
                LocalDate.parse("2015-11-30", DateUtils.DATE_FORMATTER));
        System.out.println(dates);
    }
}