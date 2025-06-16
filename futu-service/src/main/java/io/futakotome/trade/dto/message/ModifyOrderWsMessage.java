package io.futakotome.trade.dto.message;

import io.futakotome.trade.dto.ws.Message;
import io.futakotome.trade.dto.ws.MessageType;

public class ModifyOrderWsMessage implements Message {
    private String accId;
    private Integer tradeEnv;
    private Integer tradeMarket;
    private String orderId;
    private Integer modifyOp;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getModifyOp() {
        return modifyOp;
    }

    public void setModifyOp(Integer modifyOp) {
        this.modifyOp = modifyOp;
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
        return MessageType.MODIFY_ORDER;
    }
}
