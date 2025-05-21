package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum TrailType {
    UNKNOWN(0, "未知类型"),
    RATIO(1, "比例"),
    AMOUNT(2, "金额");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> TRAIN_TYPE_MAP = new HashMap<Integer, String>();

    static {
        for (TrailType type : TrailType.values()) {
            TRAIN_TYPE_MAP.put(type.code, type.name);
        }
    }

    TrailType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code) {
        return TRAIN_TYPE_MAP.getOrDefault(code, "未知跟踪类型");
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
