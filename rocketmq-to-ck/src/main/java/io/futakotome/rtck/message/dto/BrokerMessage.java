package io.futakotome.rtck.message.dto;

public class BrokerMessage implements Message {
//    private BrokerMessageContent content;

    @Override
    public MessageType getType() {
        return MessageType.RT_BROKERS;
    }

//    public BrokerMessageContent getContent() {
//        return content;
//    }
//
//    public void setContent(BrokerMessageContent content) {
//        this.content = content;
//    }
}
