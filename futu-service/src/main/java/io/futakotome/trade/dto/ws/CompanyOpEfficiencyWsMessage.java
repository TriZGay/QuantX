package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CompanyOpEfficiencyContent;

public class CompanyOpEfficiencyWsMessage implements Message {
    private Integer market;
    private String code;
    private String nextKey;
    private Integer num;
    private String currencyCode;
    private Integer financialType;
    private CompanyOpEfficiencyContent content;

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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getFinancialType() {
        return financialType;
    }

    public void setFinancialType(Integer financialType) {
        this.financialType = financialType;
    }

    public CompanyOpEfficiencyContent getContent() {
        return content;
    }

    public void setContent(CompanyOpEfficiencyContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.COMPANY_OP_EFFICIENCY;
    }
}
