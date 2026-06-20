package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CorporateActionsDividendContent;

import java.util.List;

public class CorporateActionsDividendsWsMessage implements Message {
    private Integer market;
    private String code;
    private List<CorporateActionsDividendContent> contents;

    public List<CorporateActionsDividendContent> getContents() {
        return contents;
    }

    public void setContents(List<CorporateActionsDividendContent> contents) {
        this.contents = contents;
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

    @Override
    public MessageType getType() {
        return MessageType.CO_ACTIONS_DIVIDEND;
    }
}
