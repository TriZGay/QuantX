package io.futakotome.trade.domain.code;

public enum Currency {
    UNKNOWN(0, "未知"),
    HKD(1, "港元"),
    USD(2, "美元"),
    CNH(3, "离岸人民币"),
    JPY(4, "日元"),
    SGD(5, "新元");

    private final Integer code;
    private final String name;

    Currency(Integer code, String name) {
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
