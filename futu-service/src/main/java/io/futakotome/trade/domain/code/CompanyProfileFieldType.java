package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum CompanyProfileFieldType {
    UNKNOWN("-1", "未知"),
    TEXT("CompanyProfileFieldType_SourceText", "文本"),
    LINK("CompanyProfileFieldType_LinkType", "链接"),
    INDEPENDENT("CompanyProfileFieldType_IndependentTitle", "独立标题");

    private final String code;
    private final String name;
    private static final Map<String, String> MAP = new HashMap<>();

    CompanyProfileFieldType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    static {
        for (CompanyProfileFieldType type : CompanyProfileFieldType.values()) {
            MAP.put(type.getCode(), type.getName());
        }
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
