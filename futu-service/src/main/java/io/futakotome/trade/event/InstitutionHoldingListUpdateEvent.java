package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.InstitutionHoldingListContent;

public class InstitutionHoldingListUpdateEvent {
    private InstitutionHoldingListContent content;

    public InstitutionHoldingListUpdateEvent(InstitutionHoldingListContent content) {
        this.content = content;
    }

    public InstitutionHoldingListContent getContent() {
        return content;
    }

    public void setContent(InstitutionHoldingListContent content) {
        this.content = content;
    }
}
