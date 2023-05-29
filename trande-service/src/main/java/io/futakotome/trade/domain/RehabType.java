package io.futakotome.trade.domain;

public enum RehabType {
    NONE(0, "不复权"),
    FORWARD(1, "前复权"),
    BACKWARD(2, "后复权");

    private final Integer code;
    private final String name;

    RehabType(Integer code, String name) {
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
