package io.futakotome.rtck.message.dto;

public class NotifyWsMessage implements Message {
    private String content;

    public NotifyWsMessage(String content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.NOTIFY;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
