package io.futakotome.trade.domain;

public enum TickerType {
    UNKNOWN(0, "未知"),
    AUTO_MATCH(1, "自动对盘"),
    LATE(2, "开始前成交盘"),
    NONE_AUTO_MATCH(3, "非自动对盘"),
    INTER_AUTO_MATCH(4, "同一证券商自动对盘"),
    INTER_NONE_AUTO_MATCH(5, "同一证券商非自动对盘"),
    ODD_LOT(6, "碎股交易"),
    AUCTION(7, "竞价交易"),
    BULK(8, "批量交易"),
    CRASH(9, "现金交易"),
    CROSS_MARKET(10, "跨市场交易"),
    BULK_SOLD(11, "批量卖出"),
    FREE_ON_BOARD(12, "离价交易"),
    RULE_127_OR_155(13, "第127条交易（纽交所规则）或第155条交易"),
    DELAY(14, "延迟交易"),
    MARKET_CENTER_CLOSE_PRICE(15, "中央收市价"),
    NEXT_DAY(16, "隔日交易"),
    MARKET_CENTER_OPENING(17, "中央开盘价交易"),
    PRIOR_REFERENCE_PRICE(18, "前参考价"),
    MARKET_CENTER_OPEN_PRICE(19, "中央开盘价"),
    SELLER(20, "卖方"),
    T(21, "T 类交易(盘前和盘后交易)"),
    EXTENDED_TRADING_HOURS(22, "延长交易时段"),
    CONTINGENT(23, "合单交易"),
    AVG_PRICE(24, "平均价成交"),
    OTC_SOLD(25, "场外售出"),
    ODD_LOT_CROSS_MARKET(26, "碎股跨市场交易"),
    DERIVATIVELY_PRICED(27, "衍生工具定价"),
    REOPENING_PRICED(28, "再开盘定价"),
    CLOSING_PRICED(29, "收盘定价"),
    COMPREHENSIVE_DELAY_PRICE(30, "综合延迟价格"),
    OVERSEAS(31, "交易的一方不是香港交易所的成员，属于场外交易");

    private final Integer code;
    private final String name;

    TickerType(Integer code, String name) {
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
