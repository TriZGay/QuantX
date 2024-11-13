package io.futakotome.trade.domain;

public enum RishStatus {
    LEVEL_1(0, "非常安全"),
    LEVEL_2(1, "安全"),
    LEVEL_3(2, "较安全"),
    LEVEL_4(3, "较低风险"),
    LEVEL_5(4, "中等风险"),
    LEVEL_6(5, "较高风险"),
    LEVEL_7(6, "预警"),
    LEVEL_8(7, "预警"),
    LEVEL_9(8, "预警");

    private final Integer code;
    private final String name;

    RishStatus(Integer code, String name) {
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
