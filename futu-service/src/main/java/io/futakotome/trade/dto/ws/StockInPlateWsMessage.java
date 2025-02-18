package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CommonSecurity;

import java.util.List;

public class StockInPlateWsMessage implements Message {
    private boolean all;
    private List<CommonSecurity> plates;

    public List<CommonSecurity> getPlates() {
        return plates;
    }

    public void setPlates(List<CommonSecurity> plates) {
        this.plates = plates;
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    @Override
    public MessageType getType() {
        return MessageType.STOCK_IN_PLATE;
    }
    
}
