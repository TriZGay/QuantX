package io.futakotome.trade.listener.vo;

import io.futakotome.trade.listener.Message;
import io.futakotome.trade.listener.MessageType;

public class RealTimeKLMessage implements Message {
    private KLMessageContent content;

    @Override
    public MessageType getType() {
        return MessageType.RT_KL;
    }

    public KLMessageContent getContent() {
        return content;
    }

    public void setContent(KLMessageContent content) {
        this.content = content;
    }
}
