package io.futakotome.trade.domain.code;

public enum StockFinancialQuarter {
    UNKNOWN(0, "未知"),
    ANNUAL(1, "年报"),
    FIRST_QUARTER(2, "一季报"),
    INTERIM(3, "中报"),
    THIRD_QUARTER(4, "三季报"),
    MOST_RECENT_QUARTER(5, "最近季报");

    private Integer code;
    private String desc;

    StockFinancialQuarter(Integer code, String desc) {
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
