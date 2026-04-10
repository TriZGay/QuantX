package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CommonSecurity;
import io.futakotome.trade.dto.message.StockContent;

import java.util.List;

public class StockInPlateWsMessage implements Message {
    private CommonSecurity plate;
    private List<StockContent> stocks;

    public List<StockContent> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockContent> stocks) {
        this.stocks = stocks;
    }

    public CommonSecurity getPlate() {
        return plate;
    }

    public void setPlate(CommonSecurity plate) {
        this.plate = plate;
    }

    @Override
    public MessageType getType() {
        return MessageType.STOCK_IN_PLATE;
    }

}
