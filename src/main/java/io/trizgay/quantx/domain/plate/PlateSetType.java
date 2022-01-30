package io.trizgay.quantx.domain.plate;

public enum PlateSetType {
    ALL("所有板块", 0),//所有板块
    INDUSTRY("行业板块", 1),//行业板块
    REGION("地域板块", 2),//地域板块,港美股市场的地域分类数据暂为空
    CONCEPT("概念板块", 3),//概念板块
    OTHER("其他板块", 4);//其他板块

    private final String name;
    private final Integer code;

    PlateSetType(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public static PlateSetType getPlateSet(Integer code) {
        for (PlateSetType type : PlateSetType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }
}
