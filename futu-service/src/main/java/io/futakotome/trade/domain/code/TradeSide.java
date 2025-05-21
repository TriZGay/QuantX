package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum TradeSide {
    UNKNOWN(0, "未知"),
    BUY(1, "买入"),
    SELL(2, "卖出"),
    SELL_SHORT(3, "卖空"),
    BUY_BACK(4, "买回");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> TRADE_SIDE_MAP = new HashMap<>();

    static {
        for (TradeSide tradeSide : TradeSide.values()) {
            TRADE_SIDE_MAP.put(tradeSide.code, tradeSide.name);
        }
    }

    TradeSide(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code) {
        return TRADE_SIDE_MAP.getOrDefault(code, "未知交易方向状态");
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
