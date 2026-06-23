package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.ShareholderHolderDetailContent;

public class ShareholderHolderDetailWsMessage implements Message {
    private Integer market;
    private String code;
    private Integer requestType;
    private String nextKey;
    private Integer num;
    private Integer sortColumn;
    private Integer sortType;
    private Integer periodId;
    private Integer holderId;
    private ShareholderHolderDetailContent content;

    public ShareholderHolderDetailContent getContent() {
        return content;
    }

    public void setContent(ShareholderHolderDetailContent content) {
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

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
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

    public Integer getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(Integer sortColumn) {
        this.sortColumn = sortColumn;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Integer getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Integer periodId) {
        this.periodId = periodId;
    }

    public Integer getHolderId() {
        return holderId;
    }

    public void setHolderId(Integer holderId) {
        this.holderId = holderId;
    }

    @Override
    public MessageType getType() {
        return MessageType.SHAREHOLDER_HOLDER_DETAIL;
    }
}
