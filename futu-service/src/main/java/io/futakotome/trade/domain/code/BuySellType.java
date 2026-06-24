package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum BuySellType {
    UNKNOWN(0, "未知"),
    NET_BUY(1, "净买入"),
    NET_SELL(2, "净卖出");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> MAP = new HashMap<>();

    BuySellType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    static {
        for (BuySellType type : BuySellType.values()) {
            MAP.put(type.getCode(), type.getName());
        }
    }

    public static String getName(Integer code) {
        return MAP.getOrDefault(code, "无此值");
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
}
