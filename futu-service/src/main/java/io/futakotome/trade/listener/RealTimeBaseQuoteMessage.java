package io.futakotome.trade.listener;

public class RealTimeBaseQuoteMessage implements Message {
    private String updateTime;

    @Override
    public MessageType getType() {
        return MessageType.RT_BASIC_QUOTE;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
