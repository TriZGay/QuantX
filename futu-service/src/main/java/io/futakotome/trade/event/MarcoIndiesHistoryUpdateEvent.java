package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.MarcoIndiesHistoryContent;

public class MarcoIndiesHistoryUpdateEvent {
    private MarcoIndiesHistoryContent content;

    public MarcoIndiesHistoryUpdateEvent(MarcoIndiesHistoryContent content) {
        this.content = content;
    }

    public MarcoIndiesHistoryContent getContent() {
        return content;
    }

    public void setContent(MarcoIndiesHistoryContent content) {
        this.content = content;
    }
}
