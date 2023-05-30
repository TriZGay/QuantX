package io.futakotome.trade.listener;

public enum MessageType {
    NOTIFY,
    JOIN_IN;

    MessageType() {
    }

    @Override
    public String toString() {
        return this.name();
    }
}
