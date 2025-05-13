package io.futakotome.trade.domain.code;

public enum SortDir {
    No(0, "不排序"),
    Ascend(1, "升序"),
    Descend(2, "降序");
    private Integer code;
    private String desc;

    SortDir(Integer code, String desc) {
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
