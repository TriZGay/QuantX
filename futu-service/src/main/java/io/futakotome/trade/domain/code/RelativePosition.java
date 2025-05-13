package io.futakotome.trade.domain.code;

public enum RelativePosition {
    Unknown(0, "未知"),
    More(1, "大于，first位于second的上方"),
    Less(2, "小于，first位于second的下方"),
    CrossUp(3, "升穿，first从下往上穿second"),
    CrossDown(4, "跌穿，first从上往下穿second");

    private Integer code;
    private String desc;

    RelativePosition(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
