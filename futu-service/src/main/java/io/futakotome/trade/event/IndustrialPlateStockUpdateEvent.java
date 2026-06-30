package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.IndustrialPlateStockContent;

public class IndustrialPlateStockUpdateEvent {
    private IndustrialPlateStockContent content;

    public IndustrialPlateStockUpdateEvent(IndustrialPlateStockContent content) {
        this.content = content;
    }

    public IndustrialPlateStockContent getContent() {
        return content;
    }

    public void setContent(IndustrialPlateStockContent content) {
        this.content = content;
    }
}
