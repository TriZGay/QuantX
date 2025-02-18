package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CommonSecurity;

import java.util.List;

public class StockOwnerPlateWsMessage implements Message {
    private List<CommonSecurity> securities;

    public List<CommonSecurity> getSecurities() {
        return securities;
    }

    public void setSecurities(List<CommonSecurity> securities) {
        this.securities = securities;
    }

    @Override
    public MessageType getType() {
        return MessageType.STOCK_OWNER_PLATE;
    }
}
