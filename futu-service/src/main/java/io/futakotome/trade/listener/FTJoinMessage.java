package io.futakotome.trade.listener;

public class FTJoinMessage implements Message {
    @Override
    public MessageType getType() {
        return MessageType.JOIN_IN;
    }

}
