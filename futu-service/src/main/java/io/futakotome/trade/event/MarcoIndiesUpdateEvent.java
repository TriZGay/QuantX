package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.MarcoIndiesContent;

public class MarcoIndiesUpdateEvent {
    private MarcoIndiesContent content;

    public MarcoIndiesUpdateEvent(MarcoIndiesContent content) {
        this.content = content;
    }

    public MarcoIndiesContent getContent() {
        return content;
    }

    public void setContent(MarcoIndiesContent content) {
        this.content = content;
    }
}
