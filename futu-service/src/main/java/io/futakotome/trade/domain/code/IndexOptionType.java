package io.futakotome.trade.domain.code;

public enum IndexOptionType {
    UNKNOWN(0, "未知"),
    NORMAL(1, "普通的指数期权"),
    SMALL(2, "小型指数期权");
    private final Integer code;
    private final String name;

    IndexOptionType(Integer code, String name) {
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
