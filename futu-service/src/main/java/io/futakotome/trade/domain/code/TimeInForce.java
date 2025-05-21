package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum TimeInForce {
    DAY(0, "当日有效"),
    GTC(1, "撤单前有效,最多持续90自然日");
    private Integer code;
    private String name;
    private static final Map<Integer, String> TIME_IN_FORCE_MAP = new HashMap<>();

    static {
        for (TimeInForce timeInForce : TimeInForce.values()) {
            TIME_IN_FORCE_MAP.put(timeInForce.getCode(), timeInForce.getName());
        }
    }

    TimeInForce(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code) {
        return TIME_IN_FORCE_MAP.getOrDefault(code, "未知订单有效期状态");
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
