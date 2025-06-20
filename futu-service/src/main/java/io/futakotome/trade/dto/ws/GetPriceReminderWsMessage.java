package io.futakotome.trade.dto.ws;

public class GetPriceReminderWsMessage implements Message {
    private Integer secMarket;//股票所属市场
    private String code;
    private Integer market;//整个市场

    public GetPriceReminderWsMessage(Integer secMarket, String code, Integer market) {
        this.secMarket = secMarket;
        this.code = code;
        this.market = market;
    }

    public Integer getSecMarket() {
        return secMarket;
    }

    public void setSecMarket(Integer secMarket) {
        this.secMarket = secMarket;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    @Override
    public MessageType getType() {
        return MessageType.GET_PRICE_REMINDER;
    }
}
