package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum StockFinancialQuarter {
    UNKNOWN(0, "未知"),
    ANNUAL(1, "年报"),
    FIRST_QUARTER(2, "一季报"),
    INTERIM(3, "中报"),
    THIRD_QUARTER(4, "三季报"),
    MOST_RECENT_QUARTER(5, "最近季报");

    private final Integer code;
    private final String desc;
    private static final Map<Integer, String> QUARTER_MAP = new HashMap<>();

    static {
        for (StockFinancialQuarter q : StockFinancialQuarter.values()) {
            QUARTER_MAP.put(q.code, q.desc);
        }
    }

    StockFinancialQuarter(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(Integer code) {
        return QUARTER_MAP.getOrDefault(code, "未知财报季度");
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
