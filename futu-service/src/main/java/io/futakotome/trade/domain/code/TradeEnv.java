package io.futakotome.trade.domain.code;

public enum TradeEnv {
    SIMULATE(0, "模拟环境"),
    REAL(1, "真实环境");

    private final Integer code;
    private final String name;

    TradeEnv(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (TradeEnv tradeEnv : values()) {
            if (tradeEnv.getCode().equals(code)) {
                return tradeEnv.getName();
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
