package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum RevenueBreakdownType {
    UNKNOW(0, "未知"),
    PRODUCT(1, "产品"),
    INDUSTRY(2, "行业"),
    REGION(4, "地区"),
    BUSINESS(8, "业务");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> MAP = new HashMap<>();

    static {
        for (RevenueBreakdownType t : RevenueBreakdownType.values()) {
            MAP.put(t.getCode(), t.getName());
        }
    }

    RevenueBreakdownType(Integer code, String name) {
        this.code = code;
        this.name = name;
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
