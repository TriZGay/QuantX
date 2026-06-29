package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.InstitutionHoldingChangeContent;

public class InstitutionHoldingChangeUpdateEvent {
    private InstitutionHoldingChangeContent content;

    public InstitutionHoldingChangeUpdateEvent(InstitutionHoldingChangeContent content) {
        this.content = content;
    }

    public InstitutionHoldingChangeContent getContent() {
        return content;
    }

    public void setContent(InstitutionHoldingChangeContent content) {
        this.content = content;
    }
}
