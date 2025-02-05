package io.futakotome.trade.dto.ws;

public class TimeShareMessage implements Message {
//    private TimeShareMessageContent content;
    @Override
    public MessageType getType() {
        return MessageType.RT_TIME_SHARE;
    }
//
//    public TimeShareMessageContent getContent() {
//        return content;
//    }
//
//    public void setContent(TimeShareMessageContent content) {
//        this.content = content;
//    }
}
