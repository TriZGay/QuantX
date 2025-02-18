package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CapitalDistributionContent;
import io.futakotome.trade.dto.message.CommonSecurity;

public class CapitalDistributionWsMessage implements Message {
    private CommonSecurity security;
    private CapitalDistributionContent content;

    public CommonSecurity getSecurity() {
        return security;
    }

    public void setSecurity(CommonSecurity security) {
        this.security = security;
    }

    public CapitalDistributionContent getContent() {
        return content;
    }

    public void setContent(CapitalDistributionContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.CAPITAL_DISTRIBUTION;
    }


}
