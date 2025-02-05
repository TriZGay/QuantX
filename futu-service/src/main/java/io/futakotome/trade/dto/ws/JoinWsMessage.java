package io.futakotome.trade.dto.ws;

public class JoinWsMessage implements Message {
    @Override
    public MessageType getType() {
        return MessageType.JOIN_IN;
    }

}
