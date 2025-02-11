package io.futakotome.trade.domain.code;

public enum TradeDateType {
    WHOLE(0, "全天交易"),
    MORNING(1, "上午交易,下午休市"),
    AFTERNOON(2, "下午交易,上午休市");

    private final Integer code;
    private final String name;

    TradeDateType(Integer code, String name) {
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
