package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.KLMessageContent;

public class KLineUpdateEvent {
    private KLMessageContent content;

    public KLineUpdateEvent() {
    }

    public KLineUpdateEvent(KLMessageContent content) {
        this.content = content;
    }

    public KLMessageContent getContent() {
        return content;
    }

    public void setContent(KLMessageContent content) {
        this.content = content;
    }
}
