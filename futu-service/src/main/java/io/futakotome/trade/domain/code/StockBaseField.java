package io.futakotome.trade.domain.code;

import java.util.HashMap;
import java.util.Map;

public enum StockBaseField {
    UNKNOWN(0, "未知", "未知"),
    STOCK_CODE(1, "股票代码", "股票代码，不能填区间上下限值"),
    STOCK_NAME(2, "股票名称", "股票名称，不能填区间上下限值"),
    CUR_PRICE(3, "最新价", "最新价（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,20]值区间"),
    CUR_PRICE_TO_HIGHEST_52_WEEKS_RATIO(4, "现价离52周高点百分比", "(现价 - 52周最高)/52周最高，对应 PC 端离52周高点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-30,-10]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    CUR_PRICE_TO_LOWEST_52_WEEKS_RATIO(5, "现价离52周低点百分比", "(现价 - 52周最低)/52周最低，对应 PC 端离52周低点百分比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[20,40]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    HIGH_PRICE_TO_HIGHEST_52_WEEKS_RATIO(6, "今日最高价离52周高点百分比", "(今日最高 - 52周最高)/52周最高（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-3,-1]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    LOW_PRICE_TO_LOWEST_52_WEEKS_RATIO(7, "今日最低价离52周低点百分比", "(今日最低 - 52周最低)/52周最低（精确到小数点后 3 位，超出部分会被舍弃）例如填写[10,70]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    VOLUME_RATIO(8, "量比", "量比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,30]值区间"),
    BID_ASK_RATIO(9, "委比", "委比（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-20,80.5]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    LOT_PRICE(10, "每手价格", "每手价格（精确到小数点后 3 位，超出部分会被舍弃）例如填写[40,100]值区间"),
    MARKET_VAL(11, "市值", "市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写[50000000,3000000000]值区间"),
    PE_ANNUAL(12, "市盈率(静态)", "市盈率(静态)（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-8,65.3]值区间"),
    PE_TTM(13, "市盈率 TTM", "市盈率 TTM（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-10,20.5]值区间"),
    PB_RATE(14, "市净率", "市净率（精确到小数点后 3 位，超出部分会被舍弃）例如填写[0.5,20]值区间"),
    CHANGE_RATE_5_MIN(15, "五分钟价格涨跌幅", "五分钟价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-5,6.3]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    CHANGE_RATE_BEGIN_YEAR(16, "年初至今价格涨跌幅", "年初至今价格涨跌幅（精确到小数点后 3 位，超出部分会被舍弃）例如填写[-50.1,400.7]值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    PS_TTM(17, "市销率(TTM)", "市销率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 500] 值区间（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%） "),
    PCF_TTM(18, "市现率(TTM)", "市现率(TTM)（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [100, 1000] 值区间 （该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）"),
    TOTAL_SHARE(19, "总股数", "总股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)"),
    FLOAT_SHARE(20, "流通股数", "流通股数（精确到小数点后 0 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间 (单位：股)"),
    FLOAT_MARKET_VAL(21, "流通市值", "流通市值（精确到小数点后 3 位，超出部分会被舍弃）例如填写 [1000000000,1000000000] 值区间（单位：元）");

    private final Integer code;
    private final String name;
    private final String desc;

    private static final Map<Integer, String> BASIC_FIELD_MAP = new HashMap<>();

    static {
        for (StockBaseField baseField : values()) {
            BASIC_FIELD_MAP.put(baseField.code, baseField.name);
        }
    }

    StockBaseField(Integer code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public static String getName(Integer code) {
        return BASIC_FIELD_MAP.getOrDefault(code, "未知属性");
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }
}
    