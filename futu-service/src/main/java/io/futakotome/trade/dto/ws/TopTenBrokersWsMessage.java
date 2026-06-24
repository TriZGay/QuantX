package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.TopTenBrokersContent;

public class TopTenBrokersWsMessage implements Message {
    private Integer market;
    private String code;
    private Integer daysBefore;
    private TopTenBrokersContent content;

    public TopTenBrokersContent getContent() {
        return content;
    }

    public void setContent(TopTenBrokersContent content) {
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

    public Integer getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(Integer daysBefore) {
        this.daysBefore = daysBefore;
    }

    @Override
    public MessageType getType() {
        return MessageType.TOP_TEN_BROKERS;
    }
}
