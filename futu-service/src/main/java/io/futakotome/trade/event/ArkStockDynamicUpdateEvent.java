package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ArkStockDynamicContent;

public class ArkStockDynamicUpdateEvent {
    private ArkStockDynamicContent content;

    public ArkStockDynamicUpdateEvent(ArkStockDynamicContent content) {
        this.content = content;
    }

    public ArkStockDynamicContent getContent() {
        return content;
    }

    public void setContent(ArkStockDynamicContent content) {
        this.content = content;
    }
}
