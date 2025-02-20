package io.futakotome.trade.domain.code;

public enum OptionAreaType {
    UNKNOWN(0, "未知"),
    AMERICA(1, "美式"),
    EUROPEAN(2, "欧式"),
    BERMUDA(3, "百慕大");
    private final Integer code;
    private final String name;

    OptionAreaType(Integer code, String name) {
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
