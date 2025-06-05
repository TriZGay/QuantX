package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.HistoryOrderContent;

public class HistoryOrdersWsMessage implements Message {
    private String accId;
    private Integer tradeEnv;
    private Integer tradeMarket;
    private HistoryOrderContent historyOrderContent;

    public HistoryOrderContent getHistoryOrderContent() {
        return historyOrderContent;
    }

    public void setHistoryOrderContent(HistoryOrderContent historyOrderContent) {
        this.historyOrderContent = historyOrderContent;
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
        return MessageType.HISTORY_ORDER;
    }
}
