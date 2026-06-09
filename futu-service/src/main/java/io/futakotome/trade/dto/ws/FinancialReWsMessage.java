package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.FinancialRevenueBreakDownContent;

public class FinancialReWsMessage implements Message {
    private Integer market;
    private String code;
    private Integer date;
    private Integer financialType;
    private String currencyCode;

    private FinancialRevenueBreakDownContent content;

    public FinancialRevenueBreakDownContent getContent() {
        return content;
    }

    public void setContent(FinancialRevenueBreakDownContent content) {
        this.content = content;
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

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getFinancialType() {
        return financialType;
    }

    public void setFinancialType(Integer financialType) {
        this.financialType = financialType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public MessageType getType() {
        return MessageType.FINANCIAL_REVENUE_BREAKDOWN;
    }
}
