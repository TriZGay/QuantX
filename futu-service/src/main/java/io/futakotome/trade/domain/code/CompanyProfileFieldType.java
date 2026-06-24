package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum CompanyProfileFieldType {
    UNKNOWN(-1, "未知"),
    TEXT(0, "文本"),
    LINK(1, "链接"),
    INDEPENDENT(2, "独立标题");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> MAP = new HashMap<>();

    CompanyProfileFieldType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    static {
        for (CompanyProfileFieldType type : CompanyProfileFieldType.values()) {
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
