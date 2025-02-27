package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.PositionMessageContent;

import java.util.List;

public class AccPositionWsMessage implements Message {
    private String accId;
    private Integer tradeEnv;
    private Integer tradeMarket;
    private List<PositionMessageContent> positions;

    public List<PositionMessageContent> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionMessageContent> positions) {
        this.positions = positions;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public Integer getTradeEnv() {
        return tradeEnv;
    }

    public void setTradeEnv(Integer tradeEnv) {
        this.tradeEnv = tradeEnv;
    }

    public Integer getTradeMarket() {
        return tradeMarket;
    }

    public void setTradeMarket(Integer tradeMarket) {
        this.tradeMarket = tradeMarket;
    }

    @Override
    public MessageType getType() {
        return MessageType.ACC_POSITION;
    }
}
