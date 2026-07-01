package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.RiseFallDistributionContent;

public class RiseFallDistributionWsMessage implements Message {
    private Integer plateMarket; //板块(优先使用)
    private String plateCode;
    private Integer market; //Qot_Common.QotMarket,市场(security未传时使用)
    private RiseFallDistributionContent content;

    public Integer getPlateMarket() {
        return plateMarket;
    }

    public void setPlateMarket(Integer plateMarket) {
        this.plateMarket = plateMarket;
    }

    public String getPlateCode() {
        return plateCode;
    }

    public void setPlateCode(String plateCode) {
        this.plateCode = plateCode;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public RiseFallDistributionContent getContent() {
        return content;
    }

    public void setContent(RiseFallDistributionContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.RISE_FALL_DISTRIBUTION;
    }
}
