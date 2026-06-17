package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum ValuationType {
    UNKNOWN(0, "未知"),
    PE(1, "市盈率"),
    PB(2, "市净率"),
    PS(3, "市销率");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> MAP = new HashMap<>();

    ValuationType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    static {
        for (ValuationType type : ValuationType.values()) {
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
