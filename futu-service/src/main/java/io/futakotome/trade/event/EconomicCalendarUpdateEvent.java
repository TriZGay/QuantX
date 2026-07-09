package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.EconomicCalendarContent;

public class EconomicCalendarUpdateEvent {
    private EconomicCalendarContent content;

    public EconomicCalendarUpdateEvent(EconomicCalendarContent content) {
        this.content = content;
    }

    public EconomicCalendarContent getContent() {
        return content;
    }

    public void setContent(EconomicCalendarContent content) {
        this.content = content;
    }
}
