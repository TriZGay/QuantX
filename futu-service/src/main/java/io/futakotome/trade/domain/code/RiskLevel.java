package io.futakotome.trade.domain.code;

public enum RiskLevel {
    UNKNOWN(-1, "未知"),
    SAFE(0, "安全"),
    WARNING(1, "预警"),
    DANGER(2, "危险"),
    ABSOLUTE_SAFE(3, "绝对安全"),
    OPT_DANGER(4, "危险（期权相关）");

    private final Integer code;
    private final String name;

    RiskLevel(Integer code, String name) {
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
