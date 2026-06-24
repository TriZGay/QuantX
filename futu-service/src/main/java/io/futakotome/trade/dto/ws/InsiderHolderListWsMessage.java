package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.InsiderHolderListContent;

public class InsiderHolderListWsMessage implements Message {
    private Integer market;
    private String code;
    private String nextKey;
    private Integer num;
    private InsiderHolderListContent content;

    public InsiderHolderListContent getContent() {
        return content;
    }

    public void setContent(InsiderHolderListContent content) {
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
        return MessageType.INSIDER_HOLDER_LIST;
    }
}
