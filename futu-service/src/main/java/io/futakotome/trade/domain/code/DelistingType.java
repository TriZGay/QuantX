package io.futakotome.trade.domain.code;

public enum DelistingType {
    DELISTED(1, "已退市"),
    LISTING(0, "在市");

    private final Integer code;
    private final String name;

    DelistingType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (DelistingType type : DelistingType.values()) {
            if (type.code.equals(code)) {
                return type.name;
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
