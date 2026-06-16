package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.MorningstarReportContent;

public class MorningstarReportWsMessage implements Message {
    private Integer market;
    private String code;
    private MorningstarReportContent content;

    public MorningstarReportContent getContent() {
        return content;
    }

    public void setContent(MorningstarReportContent content) {
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
        return MessageType.RESEARCH_MORNINGSTAR_REPORT;
    }
}
