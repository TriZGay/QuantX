package io.futakotome.trade.listener.vo;

import io.futakotome.trade.listener.Message;
import io.futakotome.trade.listener.MessageType;

public class TimeShareMessage implements Message {
    private TimeShareMessageContent content;
    @Override
    public MessageType getType() {
        return MessageType.RT_TIME_SHARE;
    }

    public TimeShareMessageContent getContent() {
        return content;
    }

    public void setContent(TimeShareMessageContent content) {
        this.content = content;
    }
}
