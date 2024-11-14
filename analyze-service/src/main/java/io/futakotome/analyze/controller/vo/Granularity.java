package io.futakotome.analyze.controller.vo;

public enum Granularity {
    MIN1(1, "1分K"),
    MIN3(2, "3分K"),
    MIN5(3, "5分K"),
    MIN30(4, "30分K"),
    MIN60(5, "60分K"),
    DAY(6, "日K"),
    WEEK(7, "周K"),
    MONTH(8, "月K"),
    QUARTER(9, "季K"),
    YEAR(10, "年K");

    private final Integer code;
    private final String name;

    Granularity(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String mapName(Integer code) {
        for (Granularity g : Granularity.values()) {
            if (code.equals(g.code)) {
                return g.name;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
