package io.futakotome.trade.listener;

public enum MessageType {
    NOTIFY,
    JOIN_IN,
    RT_BASIC_QUOTE,
    RT_KL;

    MessageType() {
    }

    @Override
    public String toString() {
        return this.name();
    }
}
