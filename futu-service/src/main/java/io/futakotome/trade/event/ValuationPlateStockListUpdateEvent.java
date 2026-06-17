package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ValuationPlateStockListContent;

public class ValuationPlateStockListUpdateEvent {
    private ValuationPlateStockListContent content;

    public ValuationPlateStockListUpdateEvent(ValuationPlateStockListContent content) {
        this.content = content;
    }

    public ValuationPlateStockListContent getContent() {
        return content;
    }

    public void setContent(ValuationPlateStockListContent content) {
        this.content = content;
    }
}
