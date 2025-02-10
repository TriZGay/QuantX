package io.futakotome.trade.dto.ws;

public enum MessageType {
    CONNECT,
    MARKET_STATE,
    KL_HISTORY_DETAIL,
    REFRESH_SUB,
    SUBSCRIPTION,
    TRADE_DATE,
    KL_HISTORY,

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
