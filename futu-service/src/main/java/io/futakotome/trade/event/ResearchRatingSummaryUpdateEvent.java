package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ResearchRatingSummaryContent;

public class ResearchRatingSummaryUpdateEvent {
    private ResearchRatingSummaryContent content;

    public ResearchRatingSummaryUpdateEvent(ResearchRatingSummaryContent content) {
        this.content = content;
    }

    public ResearchRatingSummaryContent getContent() {
        return content;
    }

    public void setContent(ResearchRatingSummaryContent content) {
        this.content = content;
    }
}
