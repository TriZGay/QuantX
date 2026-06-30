package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.IndustrialChainDetailContent;

public class IndustrialChainDetailUpdateEvent {
    private IndustrialChainDetailContent content;

    public IndustrialChainDetailUpdateEvent(IndustrialChainDetailContent content) {
        this.content = content;
    }

    public IndustrialChainDetailContent getContent() {
        return content;
    }

    public void setContent(IndustrialChainDetailContent content) {
        this.content = content;
    }
}
