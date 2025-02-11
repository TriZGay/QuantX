package io.futakotome.trade.domain.code;

public enum MarketType {
    UNKNOWN(0, "未知"),
    HK(1, "香港市场"),
    US(11, "美国市场"),
    CN_SH(21, "上海市场"),
    CN_SZ(22, "深圳市场"),
    SG(31, "新加坡市场"),
    JP(41, "日本市场");

    private final Integer code;
    private final String name;

    MarketType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (MarketType type : MarketType.values()) {
            if (type.getCode().equals(code)) {
                return type.getName();
            }
        }
        return "";
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
