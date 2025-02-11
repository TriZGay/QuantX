package io.futakotome.trade.domain.code;

public enum BrokersAskOrBid {
    ASK(1, "卖盘"),
    BID(2, "买盘");

    private final Integer code;
    private final String name;

    BrokersAskOrBid(Integer code, String name) {
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
