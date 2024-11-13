package io.futakotome.rtck.message.dto;

public enum MessageType {
    NOTIFY,
    JOIN_IN,
    MARKET_STATE,
    RT_BASIC_QUOTE,
    RT_KL,
    RT_TICKER,
    RT_TIME_SHARE,
    RT_BROKERS,
    KL_HISTORY_DETAIL;

    MessageType() {
    }

    @Override
    public String toString() {
        return this.name();
    }
}
