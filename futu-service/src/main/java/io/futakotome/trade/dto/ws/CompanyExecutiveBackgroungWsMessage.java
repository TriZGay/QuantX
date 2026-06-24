package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.CompanyExecutiveBackgroundContent;

public class CompanyExecutiveBackgroungWsMessage implements Message {
    private Integer market;
    private String code;
    private String leaderName;
    private CompanyExecutiveBackgroundContent content;

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

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public CompanyExecutiveBackgroundContent getContent() {
        return content;
    }

    public void setContent(CompanyExecutiveBackgroundContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.COMPANY_EXECUTIVE_BACKGROUND;
    }
}
