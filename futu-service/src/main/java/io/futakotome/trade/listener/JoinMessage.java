package io.futakotome.trade.listener;

public class JoinMessage implements Message {
    @Override
    public MessageType getType() {
        return MessageType.JOIN_IN;
    }

}
