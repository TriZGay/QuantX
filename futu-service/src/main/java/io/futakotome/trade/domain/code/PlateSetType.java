package io.futakotome.trade.domain.code;

public enum PlateSetType {
    ALL(0, "全部"),
    INDUSTRY(1, "行业板块"),
    REGION(2, "地域板块"),
    CONCEPT(3, "概念板块"),
    OTHER(4, "其他板块");

    private final Integer code;
    private final String name;

    PlateSetType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (PlateSetType type : PlateSetType.values()) {
            if (type.getCode().equals(code)) {
                return type.getName();
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
