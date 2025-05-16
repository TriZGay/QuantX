package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.AccFundsContent;

public class AccFundsWsMessage implements Message {
    private String accId;
    private Integer tradeEnv;
    private Integer tradeMarket;

    private AccFundsContent accFundsContent;

    public AccFundsContent getAccFundsContent() {
        return accFundsContent;
    }

    public void setAccFundsContent(AccFundsContent accFundsContent) {
        this.accFundsContent = accFundsContent;
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
        return MessageType.ACC_FUNDS;
    }
}
