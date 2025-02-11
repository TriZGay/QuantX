package io.futakotome.trade.domain.code;

public enum KLType {
    UNKNOWN(0, "未知"),
    MIN_1(1, "1分K"),
    DAY(2, "日K"),
    WEEK(3, "周K"),
    MONTH(4, "月K"),
    YEAR(5, "年K"),
    MIN_5(6, "5分K"),
    MIN_15(7, "15分K"),
    MIN_30(8, "30分K"),
    MIN_60(9, "60分K"),
    MIN_3(10, "3分K"),
    QUARTER(11, "季K");

    private Integer code;
    private String name;

    KLType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
