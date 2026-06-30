package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.ArkFundHoldingContent;

public class ArkFundHoldingUpdateEvent {
    private ArkFundHoldingContent content;

    public ArkFundHoldingUpdateEvent(ArkFundHoldingContent content) {
        this.content = content;
    }

    public ArkFundHoldingContent getContent() {
        return content;
    }

    public void setContent(ArkFundHoldingContent content) {
        this.content = content;
    }
}
