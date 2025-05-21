package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum TradeSecurityMarket {
    UNKNOWN(0, "未知"),
    HK(1, "香港市场（股票、窝轮、牛熊、期权、期货等）"),
    US(2, "美国市场（股票、期权、期货等）"),
    CN_SH(31, "沪股市场（股票）"),
    CN_SZ(32, "深股市场（股票）"),
    SG(41, "新加坡市场（期货）"),
    JP(51, "日本市场（期货）"),
    AU(61, "澳大利亚"),
    MY(71, "马来西亚"),
    CA(81, "加拿大"),
    FX(91, "外汇");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> TRADE_SEC_MARKET_MAP = new HashMap<>();

    static {
        for (TradeSecurityMarket tradeMarket : TradeSecurityMarket.values()) {
            TRADE_SEC_MARKET_MAP.put(tradeMarket.getCode(), tradeMarket.getName());
        }
    }

    TradeSecurityMarket(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code) {
        return TRADE_SEC_MARKET_MAP.getOrDefault(code, "未知证券所属市场");
    }

    public static String getNameByCode(Integer code) {
        for (TradeSecurityMarket market : TradeSecurityMarket.values()) {
            if (market.getCode().equals(code)) {
                return market.getName();
            }
        }
        return "";
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
