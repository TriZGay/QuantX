package io.futakotome.trade.domain;

public enum SimAccType {
    UNKNOWN(0, "未知"),
    STOCK(1, "股票模拟账户（仅用于交易证券类产品，不支持交易期权）"),
    OPTION(2, "期权模拟账户（仅用于交易期权，不支持交易股票证券类产品）");

    private final Integer code;
    private final String name;

    SimAccType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
