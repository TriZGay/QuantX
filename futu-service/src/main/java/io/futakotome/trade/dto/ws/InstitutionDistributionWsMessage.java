package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.InstitutionDistributionContent;

public class InstitutionDistributionWsMessage implements Message {
    private Integer market;              // Qot_Common.QotMarket
    private Integer institutionId;       // 机构ID
    private InstitutionDistributionContent content;

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

    public InstitutionDistributionContent getContent() {
        return content;
    }

    public void setContent(InstitutionDistributionContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.INSTITUTION_DISTR;
    }
}
