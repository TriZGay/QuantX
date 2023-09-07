package io.futakotome.trade.domain;

public enum PeriodType {
    REALTIME(0, "实时(日内)"),
    DAY(1, "日"),
    WEEK(2, "周"),
    MONTH(3, "月");

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
