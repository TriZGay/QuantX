package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.FinancialStatementsContent;

public class FinancialStatementsUpdateEvent {
    private FinancialStatementsContent content;

    public FinancialStatementsUpdateEvent(FinancialStatementsContent content) {
        this.content = content;
    }

    public FinancialStatementsContent getContent() {
        return content;
    }

    public void setContent(FinancialStatementsContent content) {
        this.content = content;
    }
}
