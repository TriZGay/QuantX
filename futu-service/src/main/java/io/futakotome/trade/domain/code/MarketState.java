package io.futakotome.trade.domain.code;

public enum MarketState {
    NONE(0, "NONE"),
    AUCTION(1, "盘前竞价"),
    WAITING_OPEN(2, "等待开价"),
    MORNING(3, "早盘"),
    RESET(4, "午间休市"),
    AFTERNOON(5, "午盘 / 美股持续交易时段"),
    CLOSED(6, "收盘"),
    PRE_MARKET_BEGIN(8, "美股盘前交易时段"),
    PRE_MARKET_END(9, "美股盘前交易结束"),
    AFTER_MARKET_BEGIN(10, "美股盘后交易时段"),
    AFTER_MARKET_END(11, "美股收盘"),
    NIGHT_OPEN(13, "夜市交易时段"),
    NIGHT_END(14, "夜市收盘"),
    FUTURE_DAY_OPEN(15, "日市交易时段"),
    FUTURE_DAY_BREAK(16, "日市休市"),
    FUTURE_DAY_CLOSE(17, "日市收盘"),
    FUTURE_DAY_WAIT_FOR_OPEN(18, "期货待开盘"),
    HK_CAS(19, "盘后竞价,港股市场增加 CAS 机制对应的市场状态"),
    FUTURE_SWITCH_DATE(22, "美期待开盘"),
    FUTURE_OPEN(23, "美期交易时段"),
    FUTURE_BREAK(24, "美期中盘休息"),
    FUTURE_BREAK_OVER(25, "美期休息后交易时段"),
    FUTURE_CLOSE(26, "美期收盘");

    private final Integer code;
    private final String name;

    MarketState(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String mapFrom(Integer code) {
        String name = "无此市场状态";
        for (MarketState state : values()) {
            if (state.getCode().equals(code)) {
                name = state.getName();
            }
        }
        return name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
