package io.futakotome.trade.domain.code;

public enum WarrantType {
    UNKNOWN(0, "未知"),
    BUY(1, "认购窝轮"),
    SELL(2, "认沽窝轮"),
    BULL(3, "牛证"),
    BEAR(4, "熊证"),
    INLINE(5, "界内证");

    private final Integer code;
    private final String name;

    WarrantType(Integer code, String name) {
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
