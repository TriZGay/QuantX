package io.futakotome.trade.domain.code;

public enum TradeSecurityMarket {
    UNKNOWN(0, "未知"),
    HK(1, "香港市场（股票、窝轮、牛熊、期权、期货等）"),
    US(2, "美国市场（股票、期权、期货等）"),
    CN_SH(31, "沪股市场（股票）"),
    CN_SZ(32, "深股市场（股票）"),
    SG(41, "新加坡市场（期货）"),
    JP(51, "日本市场（期货）");

    private final Integer code;
    private final String name;

    TradeSecurityMarket(Integer code, String name) {
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
