package io.futakotome.trade.domain;

public enum PeriodType {
    UNKNOWN(0, "未知"),
    REALTIME(1, "实时(日内)"),
    DAY(2, "日"),
    WEEK(3, "周"),
    MONTH(4, "月");

    private final int code;
    private final String name;

    PeriodType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
