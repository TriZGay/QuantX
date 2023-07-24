package io.futakotome.rtck.message;

public class JoinWsMessage implements Message {
    @Override
    public MessageType getType() {
        return MessageType.JOIN_IN;
    }

}
