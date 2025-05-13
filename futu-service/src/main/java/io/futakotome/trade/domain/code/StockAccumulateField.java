package io.futakotome.trade.domain.code;

public enum StockAccumulateField {
    UNKNOWN(0, "未知"),
    CHANGE_RATE(1, "涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10.2,20.4]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    AMPLITUDE(2, "振幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20.6]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    VOLUME(3, "日均成交量（精确到小数点后 0 位，超出部分会被舍弃）例如填写[2000,70000]值区间"),
    TURNOVER(4, "日均成交额（精确到小数点后 3 位，超出部分会被舍弃）例如填写[1400,890000]值区间"),
    TURNOVER_RATE(5, "换手率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[2,30]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）");

    private Integer code;
    private String desc;

    StockAccumulateField(Integer code, String desc) {
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
