package io.futakotome.trade.listener;

public class NotifyMessage implements Message {
    private String code;
    private String content;

    @Override
    public MessageType getType() {
        return MessageType.NOTIFY;
    }

    public NotifyMessage(String code, String content) {
        this.code = code;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
