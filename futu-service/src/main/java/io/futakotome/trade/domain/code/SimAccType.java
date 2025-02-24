package io.futakotome.trade.domain.code;

public enum SimAccType {
    UNKNOWN(0, "未知"),
    STOCK(1, "股票模拟账户"),
    OPTION(2, "期权模拟账户"),
    FUTURES(3, "期货模拟账户");
    private final Integer code;
    private final String name;

    SimAccType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (SimAccType simAccType : values()) {
            if (simAccType.getCode().equals(code)) {
                return simAccType.getName();
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
