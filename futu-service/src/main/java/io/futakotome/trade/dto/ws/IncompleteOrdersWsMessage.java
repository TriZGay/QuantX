package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.HistoryOrderContent;
import io.futakotome.trade.dto.message.IncompleteOrderContent;

public class IncompleteOrdersWsMessage implements Message {
    private String accId;
    private Integer tradeEnv;
    private Integer tradeMarket;
    private IncompleteOrderContent incompleteOrderContent;

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

    public IncompleteOrderContent getIncompleteOrderContent() {
        return incompleteOrderContent;
    }

    public void setIncompleteOrderContent(IncompleteOrderContent incompleteOrderContent) {
        this.incompleteOrderContent = incompleteOrderContent;
    }

    @Override
    public MessageType getType() {
        return MessageType.INCOMPLETE_ORDER;
    }
}
