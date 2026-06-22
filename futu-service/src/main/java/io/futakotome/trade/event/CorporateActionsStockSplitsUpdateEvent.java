package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.CorporateActionsStockSplitsContent;

public class CorporateActionsStockSplitsUpdateEvent {
    private CorporateActionsStockSplitsContent content;

    public CorporateActionsStockSplitsUpdateEvent(CorporateActionsStockSplitsContent content) {
        this.content = content;
    }

    public CorporateActionsStockSplitsContent getContent() {
        return content;
    }

    public void setContent(CorporateActionsStockSplitsContent content) {
        this.content = content;
    }
}
