package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum MarcoRegion {
    UNKNOW(0, "未知"),
    HK(1, "香港"),
    US(2, "美国"),
    JP(3, "日本"),
    SG(4, "新加坡"),
    AU(5, "澳大利亚"),
    CA(6, "加拿大"),
    MY(7, "马来西亚"),
    CN(8, "中国(沪深)");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> MAP = new HashMap<>();

    static {
        for (MarcoRegion type : MarcoRegion.values()) {
            MAP.put(type.getCode(), type.getName());
        }
    }

    MarcoRegion(Integer code, String name) {
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
