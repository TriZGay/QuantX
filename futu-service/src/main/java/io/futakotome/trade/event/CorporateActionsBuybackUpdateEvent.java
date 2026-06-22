package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.CorporateActionsBuybackContent;

public class CorporateActionsBuybackUpdateEvent {
    private CorporateActionsBuybackContent content;

    public CorporateActionsBuybackUpdateEvent(CorporateActionsBuybackContent content) {
        this.content = content;
    }

    public CorporateActionsBuybackContent getContent() {
        return content;
    }

    public void setContent(CorporateActionsBuybackContent content) {
        this.content = content;
    }
}
