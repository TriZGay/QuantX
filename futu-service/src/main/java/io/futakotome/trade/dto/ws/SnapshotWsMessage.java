package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CommonSecurity;

import java.util.List;

public class SnapshotWsMessage implements Message {
    private List<CommonSecurity> securities;

    public List<CommonSecurity> getSecurities() {
        return securities;
    }

    public void setSecurities(List<CommonSecurity> securities) {
        this.securities = securities;
    }

    @Override
    public MessageType getType() {
        return MessageType.SNAPSHOT;
    }
}
