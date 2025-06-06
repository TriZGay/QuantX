package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum StockFinancialField {
    // 基础财务属性
    Unknown(0, "未知", "未知"),
    NetProfit(1, "净利润", "净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,2500000000]值区间"),
    NetProfitGrowth(2, "净利润增长率", "净利润增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,300]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    SumOfBusiness(3, "营业收入", "营业收入 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[100000000,6400000000]值区间"),
    SumOfBusinessGrowth(4, "营收同比增长率", "营收同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,200]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    NetProfitRate(5, "净利率", "净利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,113]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    GrossProfitRate(6, "毛利率", "毛利率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[4,65]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    DebtAssetsRate(7, "资产负债率", "资产负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[5,470]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    ReturnOnEquityRate(8, "净资产收益率", "净资产收益率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,230]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    // 盈利能力属性
    ROIC(9, "投入资本回报率", "投入资本回报率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    ROATTM(10, "资产回报率(TTM)", "资产回报率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）"),
    EBITTTM(11, "息税前利润(TTM)", "息税前利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）"),
    EBITDA(12, "税息折旧及摊销前利润", "税息折旧及摊销前利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）"),
    OperatingMarginTTM(13, "营业利润率(TTM)", "营业利润率(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）"),
    EBITMargin(14, "EBIT 利润率", "EBIT 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    EBITDAMargin(15, "EBITDA 利润率", "EBITDA 利润率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    FinancialCostRate(16, "财务成本率", "财务成本率（精确到小数点后 3 位，超出部分会被舍弃） 例如填写 [1.0,10.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    OperatingProfitTTM(17, "营业利润(TTM)", "营业利润(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）"),
    ShareholderNetProfitTTM(18, "归属于母公司的净利润", "归属于母公司的净利润 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 （单位：元。仅适用于年报。）"),
    NetProfitCashCoverTTM(19, "盈利中的现金收入比例", "盈利中的现金收入比例 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,60.0] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。仅适用于年报。）"),
    // 偿债能力属性
    CurrentRatio(20, "流动比率", "流动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    QuickRatio(21, "速动比率", "速动比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,250] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    // 清债能力属性
    CurrentAssetRatio(22, "流动资产率", "流动资产率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    CurrentDebtRatio(23, "流动负债率", "流动负债率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    EquityMultiplier(24, "权益乘数", "权益乘数 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100,180] 值区间"),
    PropertyRatio(25, "产权比率", "产权比率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    CashAndCashEquivalents(26, "现金和现金等价", "现金和现金等价 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）"),
    // 运营能力属性
    TotalAssetTurnover(27, "总资产周转率", "总资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    FixedAssetTurnover(28, "固定资产周转率", "固定资产周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    InventoryTurnover(29, "存货周转率", "存货周转率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [50,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    OperatingCashFlowTTM(30, "经营活动现金流(TTM)", "经营活动现金流(TTM) （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元。仅适用于年报。）"),
    AccountsReceivable(31, "应收账款净额", "应收账款净额 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 例如填写 [1000000000,1000000000] 值区间 （单位：元）"),
    // 成长能力属性
    EBITGrowthRate(32, "EBIT 同比增长率", "EBIT 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    OperatingProfitGrowthRate(33, "营业利润同比增长率", "营业利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    TotalAssetsGrowthRate(34, "总资产同比增长率", "总资产同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    ProfitToShareholdersGrowthRate(35, "归母净利润同比增长率", "归母净利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    ProfitBeforeTaxGrowthRate(36, "总利润同比增长率", "总利润同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    EPSGrowthRate(37, "EPS 同比增长率", "EPS 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    ROEGrowthRate(38, "ROE 同比增长率", "ROE 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    ROICGrowthRate(39, "ROIC 同比增长率", "ROIC 同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    NOCFGrowthRate(40, "经营现金流同比增长率", "经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    NOCFPerShareGrowthRate(41, "每股经营现金流同比增长率", "每股经营现金流同比增长率 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1.0,10.0] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),

    // 现金流属性
    OperatingRevenueCashCover(42, "经营现金收入比", "经营现金收入比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    OperatingProfitToTotalProfit(43, "营业利润占比", "营业利润占比 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [10,100] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),

    // 市场表现属性
    BasicEPS(44, "基本每股收益", "基本每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）"),
    DilutedEPS(45, "稀释每股收益", "稀释每股收益 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）"),
    NOCFPerShare(46, "每股经营现金净流量", "每股经营现金净流量 （精确到小数点后 3 位，超出部分会被舍弃）例如填写 [0.1,10] 值区间（单位：元）");

    private final Integer code;
    private final String name;
    private final String desc;
    private static final Map<Integer, String> FINANCIAL_FIELD_MAP = new HashMap<>();

    static {
        for (StockFinancialField stockFinancialField : StockFinancialField.values()) {
            FINANCIAL_FIELD_MAP.put(stockFinancialField.code, stockFinancialField.name);
        }
    }

    StockFinancialField(Integer code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public static String getName(Integer code) {
        return FINANCIAL_FIELD_MAP.getOrDefault(code, "未知财务属性");
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
