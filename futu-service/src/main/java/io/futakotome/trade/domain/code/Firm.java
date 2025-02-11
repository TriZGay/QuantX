package io.futakotome.trade.domain.code;

public enum Firm {
    UNKNOWN(0, "未知"),
    FUTU_SECURITIES(1, "富途证券（香港）"),
    FUTU_INC(2, "富途证券（美国）"),
    FUTU_SG(3, "富途证券（新加坡）");

    private final Integer code;
    private final String name;

    Firm(Integer code, String name) {
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
