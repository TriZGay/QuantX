package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.ResearchRatingSummaryContent;

public class ResearchRatingSummaryWsMessage implements Message {
    private Integer market;
    private String code;
    //1-机构维度（默认）  2-分析师维度
    private Integer ratingDimensionType;
    private String uid;
    private String nextKey;
    private Integer num;
    private ResearchRatingSummaryContent content;

    public ResearchRatingSummaryContent getContent() {
        return content;
    }

    public void setContent(ResearchRatingSummaryContent content) {
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

    public Integer getRatingDimensionType() {
        return ratingDimensionType;
    }

    public void setRatingDimensionType(Integer ratingDimensionType) {
        this.ratingDimensionType = ratingDimensionType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
        return MessageType.RESEARCH_RATING_SUMMARY;
    }
}
