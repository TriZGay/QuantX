package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.FinancialEarningPriceHistoryContent;

import java.util.List;

public class FinancialEarningPriceHistoryWsMessage implements Message {
    private Integer market;
    private String code;
    private List<FinancialEarningPriceHistoryContent> contents;

    public List<FinancialEarningPriceHistoryContent> getContents() {
        return contents;
    }

    public void setContents(List<FinancialEarningPriceHistoryContent> contents) {
        this.contents = contents;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public MessageType getType() {
        return MessageType.FINANCIAL_EARNING_PRICE_HISTORY;
    }
}
