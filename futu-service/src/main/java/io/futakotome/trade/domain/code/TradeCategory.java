package io.futakotome.trade.domain.code;

public enum TradeCategory {
    UNKNOWN(0, "未知"),
    SECURITY(1, "证券"),
    FUTURE(2, "期货");
    private final Integer code;
    private final String name;

    TradeCategory(Integer code, String name) {
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
