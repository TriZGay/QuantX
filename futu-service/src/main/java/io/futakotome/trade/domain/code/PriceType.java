package io.futakotome.trade.domain.code;

public enum PriceType {
    UNKNOWN(0, "未知"),
    OUTSIDE(1, "价外，界内证表示界外"),
    WITHIN(2, "价内，界内证表示界内");
    private final Integer code;
    private final String name;

    PriceType(Integer code, String name) {
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
