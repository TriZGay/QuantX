package io.futakotome.trade.listener;

public class NotifyMessage implements Message {
    private String content;

    @Override
    public MessageType getType() {
        return MessageType.NOTIFY;
    }

    public NotifyMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
