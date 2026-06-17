package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.ValuationDetailContent;

public class ValuationDetailWsMessage implements Message {
    private Integer market;
    private String code;
    private Integer valuationType;
    private Integer intervalType;
    private ValuationDetailContent content;

    public ValuationDetailContent getContent() {
        return content;
    }

    public void setContent(ValuationDetailContent content) {
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

    public Integer getValuationType() {
        return valuationType;
    }

    public void setValuationType(Integer valuationType) {
        this.valuationType = valuationType;
    }

    public Integer getIntervalType() {
        return intervalType;
    }

    public void setIntervalType(Integer intervalType) {
        this.intervalType = intervalType;
    }

    @Override
    public MessageType getType() {
        return MessageType.VALUATION_DETAIL;
    }
}
