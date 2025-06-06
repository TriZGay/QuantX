package io.futakotome.trade.domain.code;

public enum TradeAccType {
    UNKNOWN(0, "未知"),
    CASH(1, "现金账户"),
    MARGIN(2, "保证金账户");

    private final Integer code;
    private final String name;

    TradeAccType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (TradeAccType type : TradeAccType.values()) {
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
