package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.ArkFundHoldingContent;

public class ArkFundHoldingWsMessage implements Message {
    private Integer holdingType;         // ArkHoldingType, 默认持仓
    private Integer cycleType;           // CycleType, 默认近1天(holdingType=持仓时忽略)
    private Integer sortField;           // SortField, 默认按持仓数量降序
    private Integer sortDir;             // SortDir
    private Integer count;               // 条数 [1,200], 默认20
    private String page;               // 翻页游标, 首次不传
    private ArkFundHoldingContent content;

    public Integer getHoldingType() {
        return holdingType;
    }

    public void setHoldingType(Integer holdingType) {
        this.holdingType = holdingType;
    }

    public Integer getCycleType() {
        return cycleType;
    }

    public void setCycleType(Integer cycleType) {
        this.cycleType = cycleType;
    }

    public Integer getSortField() {
        return sortField;
    }

    public void setSortField(Integer sortField) {
        this.sortField = sortField;
    }

    public Integer getSortDir() {
        return sortDir;
    }

    public void setSortDir(Integer sortDir) {
        this.sortDir = sortDir;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public ArkFundHoldingContent getContent() {
        return content;
    }

    public void setContent(ArkFundHoldingContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.ARK_FUND_HOLDING;
    }
}
