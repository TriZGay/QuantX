package io.futakotome.trade.domain;

public enum TradeSide {
    UNKNOWN(0, "未知"),
    BUY(1, "买入"),
    SELL(2, "卖出"),
    SELL_SHORT(3, "卖空"),
    BUY_BACK(4, "买回");

    private final Integer code;
    private final String name;

    TradeSide(Integer code, String name) {
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
