package io.futakotome.trade.dto.ws;


public class StockInPlateByMarketWsMessage implements Message {
    private Integer market;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    @Override
    public MessageType getType() {
        return MessageType.STOCK_IN_PLATE_BY_MARKET;
    }
}
