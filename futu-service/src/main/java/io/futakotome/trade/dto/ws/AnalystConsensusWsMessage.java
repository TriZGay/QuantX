package io.futakotome.trade.dto.ws;


public class AnalystConsensusWsMessage implements Message {
    private Integer market;
    private String code;
    private AnalystConsensusContent content;

    public AnalystConsensusContent getContent() {
        return content;
    }

    public void setContent(AnalystConsensusContent content) {
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
        return MessageType.ANALYST_CONSENSUS;
    }
}
