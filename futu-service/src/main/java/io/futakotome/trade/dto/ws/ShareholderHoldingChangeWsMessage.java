package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.ShareholderHoldingChangeContent;

public class ShareholderHoldingChangeWsMessage implements Message {
    private Integer market;
    private String code;
    private String nextKey;
    private Integer num;
    private Integer sortType;
    private Integer sortColumn;
    private Integer filterType;
    private ShareholderHoldingChangeContent content;

    public ShareholderHoldingChangeContent getContent() {
        return content;
    }

    public void setContent(ShareholderHoldingChangeContent content) {
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

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public Integer getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(Integer sortColumn) {
        this.sortColumn = sortColumn;
    }

    public Integer getFilterType() {
        return filterType;
    }

    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    @Override
    public MessageType getType() {
        return MessageType.SHAREHOLDER_HOLDING_CHANGE;
    }
}
