package io.futakotome.trade.domain.code;

public enum TradeDateMarketType {
    UNKNOWN(0, "未知"),
    HK(1, "香港市场"),
    US(2, "美国市场"),
    CN(3, "A股"),
    NT(4, "深(沪)股通"),
    ST(5, "港股通(深、沪)"),
    JP_FUTURE(6, "日本期货"),
    SG_FUTURE(7, "新加坡期货");

    private final Integer code;
    private final String name;

    TradeDateMarketType(Integer code, String name) {
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
