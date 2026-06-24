package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CompanyExecutivesContent;

public class CompanyExecutivesWsMessage implements Message {
    private Integer market;
    private String code;
    private CompanyExecutivesContent content;

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

    public CompanyExecutivesContent getContent() {
        return content;
    }

    public void setContent(CompanyExecutivesContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.COMPANY_EXECUTIVES;
    }
}
