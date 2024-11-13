package io.futakotome.trade.domain;

public enum CompanyAct {
    NONE(0, "无"),
    SPLIT(1, "拆股"),
    JOIN(2, "合股"),
    BONUS(4, "送股"),
    TRANSFER(8, "转增股"),
    ALLOT(16, "配股"),
    ADD(32, "增发股"),
    DIVIDEND(64, "现金分红"),
    SP_DIVIDEND(128, "特别股息");

    private final Integer code;
    private final String name;

    CompanyAct(Integer code, String name) {
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
