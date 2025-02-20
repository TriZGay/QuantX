package io.futakotome.trade.domain.code;

public enum AssetClass {
    UNKNOWN(0, "未知"),
    STOCK(1, "股票"),
    BOND(2, "债券"),
    COMMODITY(3, "商品"),
    CURRENCY_MARKET(4, "货币市场"),
    FUTURE(5, "期货"),
    SWAP(6, "掉期(互换)");

    private final Integer code;
    private final String name;

    AssetClass(Integer code, String name) {
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
