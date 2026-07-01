package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.RiseFallDistributionContent;

public class RiseFallDistributionUpdateEvent {
    private RiseFallDistributionContent content;

    public RiseFallDistributionUpdateEvent(RiseFallDistributionContent content) {
        this.content = content;
    }

    public RiseFallDistributionContent getContent() {
        return content;
    }

    public void setContent(RiseFallDistributionContent content) {
        this.content = content;
    }
}
