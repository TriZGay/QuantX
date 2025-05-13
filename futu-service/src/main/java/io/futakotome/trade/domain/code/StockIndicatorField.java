package io.futakotome.trade.domain.code;

public enum StockIndicatorField {
    UNKNOWN(0, "未知"),
    PRICE(1, "最新价格"),
    MA5(2, "5日简单均线（不建议使用）"),
    MA10(3, "10日简单均线（不建议使用）"),
    MA20(4, "20日简单均线（不建议使用）"),
    MA30(5, "30日简单均线（不建议使用）"),
    MA60(6, "60日简单均线（不建议使用）"),
    MA120(7, "120日简单均线（不建议使用）"),
    MA250(8, "250日简单均线（不建议使用）"),
    RSI(9, "RSI 指标参数的默认值为[12]"),
    EMA5(10, "5日指数移动均线（不建议使用）"),
    EMA10(11, "10日指数移动均线（不建议使用）"),
    EMA20(12, "20日指数移动均线（不建议使用）"),
    EMA30(13, "30日指数移动均线（不建议使用）"),
    EMA60(14, "60日指数移动均线（不建议使用）"),
    EMA120(15, "120日指数移动均线（不建议使用）"),
    EMA250(16, "250日指数移动均线（不建议使用）"),
    VALUE(17, "自定义数值（stock_field1不支持此字段）"),
    MA(30, "简单均线"),
    EMA(40, "指数移动均线"),
    KDJ_K(50, "KDJ 指标的 K 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]"),
    KDJ_D(51, "KDJ 指标的 D 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]"),
    KDJ_J(52, "KDJ 指标的 J 值。指标参数需要根据 KDJ 进行传参。不传则默认为 [9,3,3]"),
    MACD_DIFF(60, "MACD 指标的 DIFF 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]"),
    MACD_DEA(61, "MACD 指标的 DEA 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]"),
    MACD(62, "MACD 指标的 MACD 值。指标参数需要根据 MACD 进行传参。不传则默认为 [12,26,9]"),
    BOLL_UPPER(70, "BOLL 指标的 UPPER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]"),
    BOLL_MIDDLER(71, "BOLL 指标的 MIDDLER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]"),
    BOLL_LOWER(72, "BOLL 指标的 LOWER 值。指标参数需要根据 BOLL 进行传参。不传则默认为 [20,2]");

    private Integer code;
    private String desc;

    StockIndicatorField(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
