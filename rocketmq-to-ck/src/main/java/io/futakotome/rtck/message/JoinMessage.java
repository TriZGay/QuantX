package io.futakotome.rtck.message;

public class JoinMessage implements Message {
    @Override
    public MessageType getType() {
        return MessageType.JOIN_IN;
    }

}
