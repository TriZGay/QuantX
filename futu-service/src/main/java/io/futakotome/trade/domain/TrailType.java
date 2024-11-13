package io.futakotome.trade.domain;

public enum TrailType {
    UNKNOWN(0, "未知类型"),
    RATIO(1, "比例"),
    AMOUNT(2, "金额");

    private final Integer code;
    private final String name;

    TrailType(Integer code, String name) {
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
