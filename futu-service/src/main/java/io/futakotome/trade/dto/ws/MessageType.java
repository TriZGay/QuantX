package io.futakotome.trade.dto.ws;

public enum MessageType {
    CONNECT,
    MARKET_STATE,
    KL_HISTORY_DETAIL,
    REFRESH_SUB,
    SUBSCRIPTION,
    TRADE_DATE,
    KL_HISTORY,
    PLATES,
    STOCK_IN_PLATE, //查板块下的股票
    STOCKS,
    STOCK_OWNER_PLATE, //查股票所属的板块
    CAPITAL_FLOW,
    CAPITAL_DISTRIBUTION,
    REHABS,
    SNAPSHOT,
    ACCOUNTS,
    ACC_SUBSCRIBE,
    ACC_POSITION,
    STOCK_FILTER,
    ACC_FUNDS,
    PLACE_ORDER,
    HISTORY_ORDER,
    INCOMPLETE_ORDER,
    USER_GROUP,//自选股分组
    USER_SECURITY,//自选股列表

    RT_BASIC_QUOTE,
    RT_KL,
    RT_TICKER,
    RT_TIME_SHARE,
    RT_BROKERS;

    MessageType() {
    }

    @Override
    public String toString() {
        return this.name();
    }
}
