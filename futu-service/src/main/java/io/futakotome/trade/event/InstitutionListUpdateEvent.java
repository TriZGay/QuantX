package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.InstitutionListContent;

public class InstitutionListUpdateEvent {
    private InstitutionListContent content;

    public InstitutionListUpdateEvent(InstitutionListContent content) {
        this.content = content;
    }

    public InstitutionListContent getContent() {
        return content;
    }

    public void setContent(InstitutionListContent content) {
        this.content = content;
    }
}
