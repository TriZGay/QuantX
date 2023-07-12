package io.futakotome.trade.listener.vo;

import io.futakotome.trade.listener.Message;
import io.futakotome.trade.listener.MessageType;

public class RealTimeTickerMessage implements Message {
    private RealTimeTickerMessageContent content;

    @Override
    public MessageType getType() {
        return MessageType.RT_TICKER;
    }

    public RealTimeTickerMessageContent getContent() {
        return content;
    }

    public void setContent(RealTimeTickerMessageContent content) {
        this.content = content;
    }
}
