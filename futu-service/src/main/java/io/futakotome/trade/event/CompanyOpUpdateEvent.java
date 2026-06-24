package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.CompanyOpEfficiencyContent;

public class CompanyOpUpdateEvent {
    private CompanyOpEfficiencyContent content;

    public CompanyOpUpdateEvent(CompanyOpEfficiencyContent content) {
        this.content = content;
    }

    public CompanyOpEfficiencyContent getContent() {
        return content;
    }

    public void setContent(CompanyOpEfficiencyContent content) {
        this.content = content;
    }
}
