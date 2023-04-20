package io.futakotome.sub.domain;

public enum SubType {
    NONE(0, "NONE"),
    BASIC(1, "基础报价"),
    ORDER_BOOK(2, "摆盘"),
    TICKER(4, "逐笔"),
    RT(5, "分时"),
    KL_DAY(6, "日 K"),
    KL_5MIN(7, "5分 K"),
    KL_15MIN(8, "15分 K"),
    KL_30MIN(9, "30分 K"),
    KL_60MIN(10, "60分 K"),
    KL_1MIN(11, "1分 K"),
    KL_WEEK(12, "周 K"),
    KL_MONTH(13, "月 K"),
    BROKER(14, "经纪队列"),
    KL_QUARTER(15, "季 K"),
    KL_YEAR(16, "年 K"),
    KL_3MIN(17, "3分 K");

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
