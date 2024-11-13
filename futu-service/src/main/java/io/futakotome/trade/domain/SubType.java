package io.futakotome.trade.domain;

public enum SubType {
    NONE(0, "NONE"),
    BASIC(1, "基础报价"),
    ORDER_BOOK(2, "摆盘"),
    TICKER(4, "逐笔"),
    RT(5, "分时"),
    KL_DAY(6, "日K"),
    KL_5MIN(7, "5分K"),
    KL_15MIN(8, "15分K"),
    KL_30MIN(9, "30分K"),
    KL_60MIN(10, "60分K"),
    KL_1MIN(11, "1分K"),
    KL_WEEK(12, "周K"),
    KL_MONTH(13, "月K"),
    BROKER(14, "经纪队列"),
    KL_QUARTER(15, "季K"),
    KL_YEAR(16, "年K"),
    KL_3MIN(17, "3分K");

    private final Integer code;
    private final String name;

    SubType(Integer code, String name) {
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
