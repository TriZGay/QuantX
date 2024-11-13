package io.futakotome.trade.domain;

public enum TickerDirection {
    UNKNOWN(0, "未知"),
    BID(1, "外盘(主动买入),即以卖一价或更高的价格成交股票"),
    ASK(2, "内盘(主动卖出),即以买一价或更低的价格成交股票"),
    NEUTRAL(3, "中性盘,即以买一价与卖一价之间的价格撮合成交");

    private final Integer code;
    private final String name;

    TickerDirection(Integer code, String name) {
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
