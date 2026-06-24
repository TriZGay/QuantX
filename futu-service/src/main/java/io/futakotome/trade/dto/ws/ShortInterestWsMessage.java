package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.ShortInterestContent;

public class ShortInterestWsMessage implements Message {
    private Integer market;
    private String code;
    private String nextKey;
    private Integer num;
    private ShortInterestContent content;

    public ShortInterestContent getContent() {
        return content;
    }

    public void setContent(ShortInterestContent content) {
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
        return MessageType.SHORT_INTEREST;
    }
}
