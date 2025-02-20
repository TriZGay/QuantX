package io.futakotome.trade.domain.code;

public enum OptionType {
    UNKNOWN(0, "未知"),
    CALL(1, "看涨期权"),
    PUT(2, "看跌期权");
    private final Integer code;
    private final String name;

    OptionType(Integer code, String name) {
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
