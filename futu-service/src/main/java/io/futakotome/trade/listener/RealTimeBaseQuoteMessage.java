package io.futakotome.trade.listener;

import io.futakotome.trade.listener.vo.BasicQuoteMessageContent;

public class RealTimeBaseQuoteMessage implements Message {
    private BasicQuoteMessageContent content;

    public RealTimeBaseQuoteMessage(BasicQuoteMessageContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.RT_BASIC_QUOTE;
    }

    public BasicQuoteMessageContent getContent() {
        return content;
    }

    public void setContent(BasicQuoteMessageContent content) {
        this.content = content;
    }
}
