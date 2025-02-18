package io.futakotome.trade.dto.ws;

public class StocksWsMessage implements Message {
    private Integer market;
    private Integer stockType;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    @Override
    public MessageType getType() {
        return MessageType.STOCKS;
    }
}
