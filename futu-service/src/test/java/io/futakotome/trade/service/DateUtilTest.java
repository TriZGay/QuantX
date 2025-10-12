package io.futakotome.trade.service;


import io.futakotome.trade.utils.DatetimeUtil;
import org.junit.Test;

public class DateUtilTest {
    @Test
    public void testDatetimeWithTimeZone() {
        String datetime = "2025-09-11T15:31+08:00[Asia/Shanghai]";
        String result = DatetimeUtil.convertDatetimeWithTimeZone(datetime);
        System.out.println(result);
    }
}
