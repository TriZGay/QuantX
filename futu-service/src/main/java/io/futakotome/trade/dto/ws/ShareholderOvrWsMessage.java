package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.ShareholderOvrContent;

public class ShareholderOvrWsMessage implements Message {
    private Integer market;
    private String code;
    private Integer periodId; // 指定报告期 ID；传 0 或不传则返回最新数据，并额外返回可用报告期列表
    private ShareholderOvrContent content;

    public ShareholderOvrContent getContent() {
        return content;
    }

    public void setContent(ShareholderOvrContent content) {
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

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    @Override
    public MessageType getType() {
        return MessageType.SHAREHOLDER_OVR;
    }
}
