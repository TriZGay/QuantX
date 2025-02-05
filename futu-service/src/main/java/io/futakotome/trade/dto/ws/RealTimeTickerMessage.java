package io.futakotome.trade.dto.ws;

public class RealTimeTickerMessage implements Message {
//    private RealTimeTickerMessageContent content;

    @Override
    public MessageType getType() {
        return MessageType.RT_TICKER;
    }

//    public RealTimeTickerMessageContent getContent() {
//        return content;
//    }
//
//    public void setContent(RealTimeTickerMessageContent content) {
//        this.content = content;
//    }
}
