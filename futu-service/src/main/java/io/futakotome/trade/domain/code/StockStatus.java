package io.futakotome.trade.domain.code;

public enum StockStatus {
    UNKNOWN(0, "未知"),
    NORMAL(1, "正常状态"),
    LISTING(2, "待上市"),
    PURCHASING(3, "申购中"),
    SUBSCRIBING(4, "认购中"),
    BEFORE_DARK_TRADE_OPENING(5, "暗盘开盘前"),
    DARK_TRADING(6, "暗盘交易中"),
    DARK_TRADE_END(7, "暗盘已收盘"),
    TO_BE_OPEN(8, "待开盘"),
    SUSPENDED(9, "停牌"),
    CALLED(10, "已收回"),
    EXPIRED_LAST_TRADING_DATE(11, "已过最后交易日"),
    EXPIRED(12, "已过期"),
    DE_LISTED(13, "已退市"),
    CHANG_TO_TEMPORARY_CODE(14, "公司行动中，交易关闭，转至临时代码交易"),
    TEMPORARY_TRADE_END(15, "临时买卖结束，交易关闭"),
    CHANGED_PLATE_TRADE_END(16, "已转板，旧代码交易关闭"),
    CHANGED_CODE_TRADE_END(17, "已换代码，旧代码交易关闭"),
    RECOVERABLE_CIRCUIT_BREAKER(18, "可恢复性熔断"),
    UNRECOVERABLE_CIRCUIT_BREAKER(19, "不可恢复性熔断"),
    AFTER_COMBINATION(20, "盘后撮合"),
    AFTER_TRANSACTION(21, "盘后交易");

    private final Integer code;
    private final String name;

    StockStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (StockStatus s : StockStatus.values()) {
            if (s.getCode().equals(code)) {
                return s.getName();
            }
        }
        return "";
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
