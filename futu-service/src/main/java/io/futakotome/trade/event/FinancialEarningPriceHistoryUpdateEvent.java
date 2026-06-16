package io.futakotome.trade.event;

import io.futakotome.trade.dto.message.FinancialEarningPriceHistoryContent;

import java.util.List;

public class FinancialEarningPriceHistoryUpdateEvent {
    private List<FinancialEarningPriceHistoryContent> contents;

    public List<FinancialEarningPriceHistoryContent> getContents() {
        return contents;
    }

    public void setContents(List<FinancialEarningPriceHistoryContent> contents) {
        this.contents = contents;
    }

    public FinancialEarningPriceHistoryUpdateEvent(List<FinancialEarningPriceHistoryContent> contents) {
        this.contents = contents;
    }
}
