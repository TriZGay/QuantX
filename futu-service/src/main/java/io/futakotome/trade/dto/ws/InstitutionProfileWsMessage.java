package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.InstitutionProfileContent;

public class InstitutionProfileWsMessage implements Message {
    private Integer market;
    private Integer institutionId;
    private InstitutionProfileContent content;

    public InstitutionProfileContent getContent() {
        return content;
    }

    public void setContent(InstitutionProfileContent content) {
        this.content = content;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    @Override
    public MessageType getType() {
        return MessageType.INSTITUTION_PROFILE;
    }
}
