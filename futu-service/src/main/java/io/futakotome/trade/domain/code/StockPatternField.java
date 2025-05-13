package io.futakotome.trade.domain.code;

public enum StockPatternField {
    Unknown(0, "未知"),
    MAAlignmentLong(1, "MA多头排列（连续两天MA5>MA10>MA20>MA30>MA60，且当日收盘价大于前一天收盘价）"),
    MAAlignmentShort(2, "MA空头排列（连续两天MA5 <MA10 <MA20 <MA30 <MA60，且当日收盘价小于前一天收盘价）"),
    EMAAlignmentLong(3, "EMA多头排列（连续两天EMA5>EMA10>EMA20>EMA30>EMA60，且当日收盘价大于前一天收盘价）"),
    EMAAlignmentShort(4, "EMA空头排列（连续两天EMA5 <EMA10 <EMA20 <EMA30 <EMA60，且当日收盘价小于前一天收盘价）"),
    RSIGoldCrossLow(5, "RSI低位金叉（50以下，短线RSI上穿长线RSI（前一日短线RSI小于长线RSI，当日短线RSI大于长线RSI））"),
    RSIDeathCrossHigh(6, "RSI高位死叉（50以上，短线RSI下穿长线RSI（前一日短线RSI大于长线RSI，当日短线RSI小于长线RSI））"),
    RSITopDivergence(7, "RSI顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的RSI12值 <前面波峰的RSI12值）"),
    RSIBottomDivergence(8, "RSI底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的RSI12值>前面波谷的RSI12值）"),
    KDJGoldCrossLow(9, "KDJ低位金叉（KDJ的值都小于或等于30，且前一日K,J值分别小于D值，当日K,J值分别大于D值）"),
    KDJDeathCrossHigh(10, "KDJ高位死叉（KDJ的值都大于或等于70，且前一日K,J值分别大于D值，当日K,J值分别小于D值）"),
    KDJTopDivergence(11, "KDJ顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的J值 <前面波峰的J值）"),
    KDJBottomDivergence(12, "KDJ底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的J值>前面波谷的J值）"),
    MACDGoldCrossLow(13, "MACD低位金叉（DIFF上穿DEA（前一日DIFF小于DEA，当日DIFF大于DEA））"),
    MACDDeathCrossHigh(14, "MACD高位死叉（DIFF下穿DEA（前一日DIFF大于DEA，当日DIFF小于DEA））"),
    MACDTopDivergence(15, "MACD顶背离（相邻的两个K线波峰，后面的波峰对应的CLOSE>前面的波峰对应的CLOSE，后面波峰的macd值 <前面波峰的macd值）"),
    MACDBottomDivergence(16, "MACD底背离（相邻的两个K线波谷，后面的波谷对应的CLOSE <前面的波谷对应的CLOSE，后面波谷的macd值>前面波谷的macd值）"),
    BOLLBreakUpper(17, "BOLL突破上轨（前一日股价低于上轨值，当日股价大于上轨值）"),
    BOLLLower(18, "BOLL突破下轨（前一日股价高于下轨值，当日股价小于下轨值）"),
    BOLLCrossMiddleUp(19, "BOLL向上破中轨（前一日股价低于中轨值，当日股价大于中轨值）"),
    BOLLCrossMiddleDown(20, "BOLL向下破中轨（前一日股价大于中轨值，当日股价小于中轨值）");

    private Integer code;
    private String desc;

    StockPatternField(Integer code, String desc) {
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
