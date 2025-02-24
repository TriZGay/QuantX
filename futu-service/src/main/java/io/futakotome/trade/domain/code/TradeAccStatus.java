package io.futakotome.trade.domain.code;

public enum TradeAccStatus {
    ACTIVE(0, "生效"),
    DISABLED(1, "失效");
    private final Integer code;
    private final String name;

    TradeAccStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (TradeAccStatus status : TradeAccStatus.values()) {
            if (status.getCode().equals(code)) {
                return status.getName();
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
