package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.CompanyExecutiveBackgroundContent;

public class CompanyExecutiveBackgroundUpdateEvent {
    private CompanyExecutiveBackgroundContent content;

    public CompanyExecutiveBackgroundUpdateEvent(CompanyExecutiveBackgroundContent content) {
        this.content = content;
    }

    public CompanyExecutiveBackgroundContent getContent() {
        return content;
    }

    public void setContent(CompanyExecutiveBackgroundContent content) {
        this.content = content;
    }
}
