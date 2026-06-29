package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.InstitutionProfileContent;

public class InstitutionProfileUpdateEvent {
    private InstitutionProfileContent content;

    public InstitutionProfileUpdateEvent(InstitutionProfileContent content) {
        this.content = content;
    }

    public InstitutionProfileContent getContent() {
        return content;
    }

    public void setContent(InstitutionProfileContent content) {
        this.content = content;
    }
}
