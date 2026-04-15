package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CommonSecurity;

import java.util.List;

public class SnapshotWsMessage implements Message {
    private Integer market;
    private Integer isPlate;
    private List<CommonSecurity> securities;

    public Integer getIsPlate() {
        return isPlate;
    }

    public void setIsPlate(Integer isPlate) {
        this.isPlate = isPlate;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

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
