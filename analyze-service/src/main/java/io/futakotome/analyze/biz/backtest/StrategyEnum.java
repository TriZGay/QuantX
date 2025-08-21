package io.futakotome.analyze.biz.backtest;

public enum StrategyEnum {
    MOVING_AVG(1, "移动均线"),
    KDJ_GOLDEN_DEAD_CROSS(2, "KDJ金叉死叉");

    private final Integer code;
    private final String name;

    StrategyEnum(Integer code, String name) {
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
