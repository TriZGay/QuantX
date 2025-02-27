package io.futakotome.trade.domain.code;

public enum PositionSide {
    LONG(0, "多仓"),
    UNKNOWN(-1, "未知"),
    SHORT(1, "空仓");

    private final Integer code;
    private final String name;

    PositionSide(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (PositionSide positionSide : PositionSide.values()) {
            if (positionSide.getCode().equals(code)) {
                return positionSide.getName();
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
