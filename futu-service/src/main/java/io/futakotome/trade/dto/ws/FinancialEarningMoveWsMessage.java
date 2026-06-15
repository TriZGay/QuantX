package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.FinancialEarningMoveContent;

import java.util.List;

public class FinancialEarningMoveWsMessage implements Message {
    private Integer market;
    private String code;
    private Integer periodCount;
    private List<FinancialEarningMoveContent> contents;

    public List<FinancialEarningMoveContent> getContents() {
        return contents;
    }

    public void setContents(List<FinancialEarningMoveContent> contents) {
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

    public Integer getPeriodCount() {
        return periodCount;
    }

    public void setPeriodCount(Integer periodCount) {
        this.periodCount = periodCount;
    }

    @Override
    public MessageType getType() {
        return MessageType.FINANCIAL_EARNING_MOVE;
    }
}
