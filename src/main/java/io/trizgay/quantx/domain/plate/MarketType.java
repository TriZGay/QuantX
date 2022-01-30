package io.trizgay.quantx.domain.plate;

public enum MarketType {
    UNKNOWN("未知市场", 0),//未知市场
    HK("香港市场", 1),//香港市场
    US("美国市场", 11),//美国市场
    CNSH("中国沪股", 21),//中国沪股
    CNSZ("中国深股", 22),//中国深股
    SG("新加坡市场", 31),//新加坡市场
    JP("日本市场", 41);//日本市场

    private final String name;
    private final Integer code;

    MarketType(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public static MarketType getMarket(Integer code) {
        for (MarketType type : MarketType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }
}
