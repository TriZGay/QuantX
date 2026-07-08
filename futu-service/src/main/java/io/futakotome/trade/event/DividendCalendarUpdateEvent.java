package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.DividendCalendarContent;

public class DividendCalendarUpdateEvent {
    private DividendCalendarContent content;

    public DividendCalendarUpdateEvent(DividendCalendarContent content) {
        this.content = content;
    }

    public DividendCalendarContent getContent() {
        return content;
    }

    public void setContent(DividendCalendarContent content) {
        this.content = content;
    }
}
