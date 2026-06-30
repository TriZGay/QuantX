package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.ArkStockDynamicContent;

public class ArkStockDynamicWsMessage implements Message {
    private Integer market;
    private String code;
    private ArkStockDynamicContent content;

    public ArkStockDynamicContent getContent() {
        return content;
    }

    public void setContent(ArkStockDynamicContent content) {
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

    @Override
    public MessageType getType() {
        return MessageType.ARK_STOCK_DYNAMIC;
    }
}
