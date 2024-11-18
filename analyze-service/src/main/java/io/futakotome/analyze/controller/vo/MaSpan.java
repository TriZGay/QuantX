package io.futakotome.analyze.controller.vo;

public enum MaSpan {
    FIVE(1, "MA5"),
    TEN(2, "MA10"),
    TWENTY(3, "MA20"),
    THIRTY(4, "MA30"),
    SIXTY(5, "MA60"),
    ONE_HUNDRED_TWENTY(6, "MA120");

    private final Integer code;
    private final String name;

    MaSpan(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String mapName(Integer code) {
        for (MaSpan s : MaSpan.values()) {
            if (code.equals(s.code)) {
                return s.name;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
