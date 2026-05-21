package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum StockType {
    Unknown(0, "未知"),
    Bond(1, "债券"),
    Bwrt(2, "一揽子权证"),
    Eqty(3, "正股"),
    Trust(4, "信托,基金"),
    Warrant(5, "窝轮"),
    Index(6, "指数"),
    Plate(7, "板块"),
    Drvt(8, "期权"),
    PlateSet(9, "板块集"),
    Future(10, "期货");

    private final Integer code;
    private final String name;
    private static final Map<Integer, String> STOCK_TYPE_MAP = new HashMap<>();

    static {
        for (StockType stockType : StockType.values()) {
            STOCK_TYPE_MAP.put(stockType.code, stockType.name);
        }
    }

    StockType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code) {
        return STOCK_TYPE_MAP.getOrDefault(code, "未知市场码表");
    }

    @Deprecated
    public static String getNameByCode(Integer code) {
        for (StockType type : StockType.values()) {
            if (type.getCode().equals(code)) {
                return type.getName();
            }
        }
        return "";
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
}
