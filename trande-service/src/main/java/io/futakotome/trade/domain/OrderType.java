package io.futakotome.trade.domain;

public enum OrderType {
    UNKNOWN(0, "未知"),
    NORMAL(1, "普通订单(港股的增强限价单、港股期权的限价单，A 股限价委托、美股的限价单，港股期货的限价单，CME 期货的限价单)。目前港股期权只能指定此订单类型"),
    MARKET(2, "市价订单(目前支持美股、港股正股、窝轮、牛熊、界内证)"),
    ABSOLUTE_LIMIT(5, "绝对限价订单（仅港股），只有价格完全匹配才成交，否则下单失败。举例：下一笔价格为 5 元的绝对限价买单，卖方的价格必须也是 5 元才能成交，卖方即使低于 5 元也不能成交，下单失败。卖出同理"),
    AUCTION(6, "竞价订单（仅港股），仅港股早盘竞价和收盘竞价有效"),
    AUCTION_LIMIT(7, "竞价限价订单（仅港股），仅早盘竞价和收盘竞价有效，参与竞价，且要求满足指定价格才会成交"),
    SPECIAL_LIMIT(8, "特别限价订单（仅港股），成交规则同增强限价订单，且部分成交后，交易所自动撤销订单"),
    SPECIAL_LIMIT_ALL(9, "特别限价且要求全部成交订单（仅港股）。全部成交，否则自动撤单"),
    STOP(10, "止损市价单"),
    STOP_LIMIT(11, "止损限价单"),
    MARKET_IF_TOUCHED(12, "触及市价单（止盈）"),
    LIMIT_IF_TOUCHED(13, "触及限价单（止盈）"),
    TRAILING_STOP(14, "跟踪止损市价单"),
    TRAILING_STOP_LIMIT(15, "跟踪止损限价单");

    private final Integer code;
    private final String name;

    OrderType(Integer code, String name) {
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
