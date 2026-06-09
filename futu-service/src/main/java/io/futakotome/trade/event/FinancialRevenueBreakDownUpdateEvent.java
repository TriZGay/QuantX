package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.FinancialRevenueBreakDownContent;

public class FinancialRevenueBreakDownUpdateEvent {
    private FinancialRevenueBreakDownContent content;

    public FinancialRevenueBreakDownUpdateEvent(FinancialRevenueBreakDownContent content) {
        this.content = content;
    }

    public FinancialRevenueBreakDownContent getContent() {
        return content;
    }

    public void setContent(FinancialRevenueBreakDownContent content) {
        this.content = content;
    }
}
