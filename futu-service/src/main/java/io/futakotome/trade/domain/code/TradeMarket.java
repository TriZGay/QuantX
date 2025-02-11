package io.futakotome.trade.domain.code;

public enum TradeMarket {
    UNKNOWN(0, "未知市场"),
    HK(1, "香港市场（证券、期权）"),
    US(2,"美国市场（证券、期权）"),
    CN(3,"A 股市场（仅用于模拟交易）"),
    HKCC(4,"A 股通市场（股票）"),
    FUTURES(5,"期货市场（环球期货）");

    private final Integer code;
    private final String name;

    TradeMarket(Integer code, String name) {
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
