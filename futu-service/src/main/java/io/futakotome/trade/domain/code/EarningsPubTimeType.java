package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum EarningsPubTimeType {
    UNKNOW(0, "未知"),
    PRE_MARKET(1, "盘前发布"),
    AFTER_MARKET(2, "盘后发布"),
    DURING_MARKET(3, "盘中发布");
    private final Integer code;
    private final String name;

    private static final Map<Integer, String> MAP = new HashMap<>();

    EarningsPubTimeType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    static {
        for (EarningsPubTimeType type : EarningsPubTimeType.values()) {
            MAP.put(type.getCode(), type.getName());
        }
    }

    public static String getName(Integer code) {
        return MAP.getOrDefault(code, "无此值");
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
