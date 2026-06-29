package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.InstitutionDistributionContent;

public class InstitutionDistributionUpdateEvent {
    private InstitutionDistributionContent content;

    public InstitutionDistributionUpdateEvent(InstitutionDistributionContent content) {
        this.content = content;
    }

    public InstitutionDistributionContent getContent() {
        return content;
    }

    public void setContent(InstitutionDistributionContent content) {
        this.content = content;
    }
}
