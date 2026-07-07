package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum DividendRank {
    Unknown(0, "未知"),
    HighYield(1, "高股息率"),      // 高股息率
    DividendGrowth(2, "股息保持增长"); // 股息保持增长 ;

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> MAP = new HashMap<>();

    DividendRank(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    static {
        for (DividendRank rank : DividendRank.values()) {
            MAP.put(rank.getCode(), rank.getName());
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
