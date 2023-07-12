package io.futakotome.trade.listener;

public enum MessageType {
    NOTIFY,
    JOIN_IN,
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
