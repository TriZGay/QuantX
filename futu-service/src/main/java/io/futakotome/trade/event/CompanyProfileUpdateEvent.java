package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.CompanyProfileContent;

public class CompanyProfileUpdateEvent {
    private CompanyProfileContent content;

    public CompanyProfileUpdateEvent(CompanyProfileContent content) {
        this.content = content;
    }

    public CompanyProfileContent getContent() {
        return content;
    }

    public void setContent(CompanyProfileContent content) {
        this.content = content;
    }
}
