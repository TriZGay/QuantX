package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum TradeMarket {
    UNKNOWN(0, "未知市场"),
    HK(1, "香港市场"),
    US(2, "美国市场"),
    CN(3, "A股市场(模拟)"),
    HKCC(4, "A股通市场(股票)"),
    FUTURES(5, "期货市场(环球期货)"),
    SG(6, "新加坡市场"),
    AU(8, "澳洲市场"),
    FUTURES_SIM_HK(10, "香港期货模拟市场"),
    FUTURES_SIM_US(11, "美国期货模拟市场"),
    FUTURES_SIM_SG(12, "新加坡期货模拟市场"),
    FUTURES_SIM_JP(13, "日本期货模拟市场"),
    JP(15, "日本市场"),
    MY(111, "马来西亚市场"),
    CA(112, "加拿大市场"),
    HK_FUND(113, "香港基金市场"),
    US_FUND(123, "美国基金市场");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> TRADE_MARKET_MAP = new HashMap<>();

    static {
        for (TradeMarket tradeMarket : TradeMarket.values()) {
            TRADE_MARKET_MAP.put(tradeMarket.getCode(), tradeMarket.getName());
        }
    }

    TradeMarket(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code) {
        return TRADE_MARKET_MAP.get(code);
    }

    public static String getNameByCode(Integer code) {
        for (TradeMarket tradeMarket : TradeMarket.values()) {
            if (tradeMarket.getCode().equals(code)) {
                return tradeMarket.getName();
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
