package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.EarningsCalendarContent;

public class EarningsCalendarUpdateEvent {
    private EarningsCalendarContent content;

    public EarningsCalendarUpdateEvent(EarningsCalendarContent content) {
        this.content = content;
    }

    public EarningsCalendarContent getContent() {
        return content;
    }

    public void setContent(EarningsCalendarContent content) {
        this.content = content;
    }
}
