package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CompanyProfileContent;

public class CompanyProfileWsMessage implements Message {
    private Integer market;
    private String code;
    private CompanyProfileContent content;

    public CompanyProfileContent getContent() {
        return content;
    }

    public void setContent(CompanyProfileContent content) {
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

    @Override
    public MessageType getType() {
        return MessageType.COMPANY_PROFILE;
    }
}
