package io.futakotome.analyze.controller.vo;

public enum Granularity {
    MIN1(1, "1分K"),
    DAY(2, "日K"),
    WEEK(3, "周K"),
    MONTH(4, "月K"),
    YEAR(5, "年K"),
    MIN5(6, "5分K"),
    MIN15(7, "15分K"),
    MIN30(8, "30分K"),
    MIN60(9, "60分K"),
    MIN3(10, "3分K"),
    QUARTER(11, "季K");

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
