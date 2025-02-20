package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CommonSecurity;
import io.futakotome.trade.dto.message.RehabMessageContent;

import java.util.List;

public class RehabsWsMessage implements Message {
    private CommonSecurity security;
    private List<RehabMessageContent> rehabs;

    public List<RehabMessageContent> getRehabs() {
        return rehabs;
    }

    public void setRehabs(List<RehabMessageContent> rehabs) {
        this.rehabs = rehabs;
    }

    public CommonSecurity getSecurity() {
        return security;
    }

    public void setSecurity(CommonSecurity security) {
        this.security = security;
    }

    @Override
    public MessageType getType() {
        return MessageType.REHABS;
    }
}
