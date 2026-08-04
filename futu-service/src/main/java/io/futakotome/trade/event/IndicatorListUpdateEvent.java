package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.IndicatorListContent;

public class IndicatorListUpdateEvent {
    private IndicatorListContent content;

    public IndicatorListUpdateEvent(IndicatorListContent content) {
        this.content = content;
    }

    public void setContent(IndicatorListContent content) {
        this.content = content;
    }

    public IndicatorListContent getContent() {
        return content;
    }
}
