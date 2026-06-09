package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum FinancialType {
    UNKNOWN(0, ""),
    Q1(1, "单季报，Q1"),
    Q2(2, "单季报，Q2"),
    Q3(3, "单季报，Q3"),
    Q4(4, "单季报，Q4"),
    Q6(5, "累计季报，Q6（Q1+Q2）"),
    Q9(6, "累计季报，Q9（Q1+Q2+Q3）"),
    ANNUAL(7, "年报"),
    QUARTERLY(8, "单季报组合（Q1, Q2, Q3, Q4）"),
    QUARTERLY_ANNUAL(9, "单季报 + 年报"),
    MUL_QUARTERLY(10, "累计季报（Q1, Q6, Q9, Annual）");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> MAP = new HashMap<>();

    static {
        for (FinancialType type : FinancialType.values()) {
            MAP.put(type.getCode(), type.name);
        }
    }

    FinancialType(Integer code, String name) {
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
