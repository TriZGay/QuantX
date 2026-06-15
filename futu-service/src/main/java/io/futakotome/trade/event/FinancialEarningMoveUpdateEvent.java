package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.FinancialEarningMoveContent;

import java.util.List;

public class FinancialEarningMoveUpdateEvent {
    private List<FinancialEarningMoveContent> contents;

    public FinancialEarningMoveUpdateEvent(List<FinancialEarningMoveContent> contents) {
        this.contents = contents;
    }

    public List<FinancialEarningMoveContent> getContents() {
        return contents;
    }

    public void setContents(List<FinancialEarningMoveContent> contents) {
        this.contents = contents;
    }
}
