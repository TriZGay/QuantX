package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.IndustrialChainListContent;

public class IndustrialChainListUpdateEvent {
    private IndustrialChainListContent content;

    public IndustrialChainListUpdateEvent(IndustrialChainListContent content) {
        this.content = content;
    }

    public IndustrialChainListContent getContent() {
        return content;
    }

    public void setContent(IndustrialChainListContent content) {
        this.content = content;
    }
}
