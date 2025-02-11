package io.futakotome.trade.domain.code;

public enum ExchangeType {
    UNKNOWN(0, "未知"),
    HK_MAIN_BOARD(1, "港交所-主板"),
    HK_GEM_BOARD(2, "港交所-创业板"),
    HK_HKEX(3, "港交所"),
    US_NYSE(4, "纽交所"),
    US_NASDAQ(5, "纳斯达克"),
    US_PINK(6, "OTC 市场"),
    US_AMEX(7, "美交所"),
    US_OPTION(8, "美国（仅美股期权适用）"),
    US_NYMEX(9, "NYMEX"),
    US_COMEX(10, "COMEX"),
    US_CBOT(11, "CBOT"),
    US_CME(12, "CME"),
    US_CBOE(13, "CBOE"),
    CN_SH(14, "上交所"),
    CN_SZ(15, "深交所"),
    CN_STIB(16, "大A科创板"),
    SG_SGX(17, "新交所"),
    JP_OSE(18, "大阪交易所");

    private final Integer code;
    private final String name;

    ExchangeType(Integer code, String name) {
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
