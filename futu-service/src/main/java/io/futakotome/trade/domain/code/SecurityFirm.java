package io.futakotome.trade.domain.code;

public enum SecurityFirm {
    UNKNOWN(0, "未知"),
    FUTU_SECURITIES(1, "富途证券（香港）"),
    FUTU_INC(2, "富途证券（美国）"),
    FUTU_SG(3, "富途证券（新加坡）");

    private final Integer code;
    private final String name;

    SecurityFirm(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        for (SecurityFirm firm : SecurityFirm.values()) {
            if (firm.getCode().equals(code)) {
                return firm.getName();
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
