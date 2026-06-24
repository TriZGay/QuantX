package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.InsiderTradeListContent;

public class InsiderTradeListWsMessage implements Message {
    private Integer market;
    private String code;
    private Long holderId;
    private String nextKey;
    private Integer num;
    private InsiderTradeListContent content;

    public InsiderTradeListContent getContent() {
        return content;
    }

    public void setContent(InsiderTradeListContent content) {
        this.content = content;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getHolderId() {
        return holderId;
    }

    public void setHolderId(Long holderId) {
        this.holderId = holderId;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public MessageType getType() {
        return MessageType.INSIDER_TRADE_LIST;
    }
}
