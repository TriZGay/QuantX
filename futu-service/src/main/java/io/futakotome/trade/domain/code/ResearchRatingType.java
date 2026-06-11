package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum ResearchRatingType {
    UNKNOW(0, "未知"),
    SELL(1, "卖出"),
    UNDER_PERFORM(2, "跑输大盘"),
    HOLD(3, "持有"),
    BUY(4, "买入"),
    STRONG_BUY(5, "强力推荐");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> MAP = new HashMap<>();

    ResearchRatingType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    static {
        for (ResearchRatingType type : ResearchRatingType.values()) {
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
