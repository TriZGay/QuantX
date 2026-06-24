package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.CompanyExecutivesContent;

public class CompanyExecutivesUpdateEvent {
    private CompanyExecutivesContent content;

    public CompanyExecutivesUpdateEvent(CompanyExecutivesContent content) {
        this.content = content;
    }

    public CompanyExecutivesContent getContent() {
        return content;
    }

    public void setContent(CompanyExecutivesContent content) {
        this.content = content;
    }
}
