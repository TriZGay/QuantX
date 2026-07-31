package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum FinancialType {
    UNKNOWN("F10Type_Unknown", ""),
    Q1("F10Type_Q1", "单季报，Q1"),
    Q2("F10Type_Q2", "单季报，Q2"),
    Q3("F10Type_Q3", "单季报，Q3"),
    Q4("F10Type_Q4", "单季报，Q4"),
    Q6("F10Type_Q6", "累计季报，Q6（Q1+Q2）"),
    Q9("F10Type_Q9", "累计季报，Q9（Q1+Q2+Q3）"),
    ANNUAL("F10Type_Annual", "年报"),
    QUARTERLY("F10Type_Quarterly", "单季报组合（Q1, Q2, Q3, Q4）"),
    QUARTERLY_ANNUAL("F10Type_QuarterlyAnnual", "单季报 + 年报"),
    MUL_QUARTERLY("F10Type_MulQuarterly", "累计季报（Q1, Q6, Q9, Annual）");

    private final String code;
    private final String name;
    private static final Map<String, String> MAP = new HashMap<>();

    static {
        for (FinancialType type : FinancialType.values()) {
            MAP.put(type.getCode(), type.name);
        }
    }

    FinancialType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(String code) {
        return MAP.getOrDefault(code, "无此值");
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
