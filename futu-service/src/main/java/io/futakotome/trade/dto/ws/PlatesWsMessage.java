package io.futakotome.trade.dto.ws;

import java.util.List;

public class PlatesWsMessage implements Message {
    private List<Integer> markets;

    public List<Integer> getMarkets() {
        return markets;
    }

    public void setMarkets(List<Integer> markets) {
        this.markets = markets;
    }

    @Override
    public MessageType getType() {
        return MessageType.PLATES;
    }
}
