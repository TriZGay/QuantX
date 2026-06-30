package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.IndustrialPlateInfoContent;

public class IndustrialPlateInfoUpdateEvent {
    private IndustrialPlateInfoContent content;

    public IndustrialPlateInfoUpdateEvent(IndustrialPlateInfoContent content) {
        this.content = content;
    }

    public IndustrialPlateInfoContent getContent() {
        return content;
    }

    public void setContent(IndustrialPlateInfoContent content) {
        this.content = content;
    }
}
