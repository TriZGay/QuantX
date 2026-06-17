package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ValuationDetailContent;

public class ValuationDetailUpdateEvent {
    private ValuationDetailContent content;

    public ValuationDetailUpdateEvent(ValuationDetailContent content) {
        this.content = content;
    }

    public ValuationDetailContent getContent() {
        return content;
    }

    public void setContent(ValuationDetailContent content) {
        this.content = content;
    }
}
