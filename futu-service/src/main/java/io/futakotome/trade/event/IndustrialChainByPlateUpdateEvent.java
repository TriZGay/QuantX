package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.IndustrialChainByPlateContent;

public class IndustrialChainByPlateUpdateEvent {
    private IndustrialChainByPlateContent content;

    public IndustrialChainByPlateUpdateEvent(IndustrialChainByPlateContent content) {
        this.content = content;
    }

    public IndustrialChainByPlateContent getContent() {
        return content;
    }

    public void setContent(IndustrialChainByPlateContent content) {
        this.content = content;
    }
}
