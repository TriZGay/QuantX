package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.HeatMapDataContent;

public class HeatMapDataUpdateEvent {
    private HeatMapDataContent content;

    public HeatMapDataUpdateEvent(HeatMapDataContent content) {
        this.content = content;
    }

    public HeatMapDataContent getContent() {
        return content;
    }

    public void setContent(HeatMapDataContent content) {
        this.content = content;
    }
}
