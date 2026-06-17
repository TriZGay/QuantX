package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.FinancialStatementsContent;

public class FinancialStatementWsMessage implements Message {
    private Integer market;
    private String code;
    private Integer statementType;
    private Integer financialType;
    private String currencyCode;
    private String nextKey;
    private Integer num;
    private FinancialStatementsContent content;

    public FinancialStatementsContent getContent() {
        return content;
    }

    public void setContent(FinancialStatementsContent content) {
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

    public Integer getStatementType() {
        return statementType;
    }

    public void setStatementType(Integer statementType) {
        this.statementType = statementType;
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

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public MessageType getType() {
        return MessageType.FINANCIAL_STATEMENTS;
    }
}
