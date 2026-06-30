package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.ArkActiveTransactionContent;

public class ArkActiveTransactionWsMessage implements Message {
    private Integer holdingType;         // HoldingType, 默认增持
    private Integer cycleType;           // CycleType, 默认近1天
    private Integer sortField;           // SortField
    private Integer sortDir;             // SortDir
    private Integer count;              // 条数 [1,200], 默认50
    private String page;              // 翻页游标, 首次不传
    private ArkActiveTransactionContent content;

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

    public ArkActiveTransactionContent getContent() {
        return content;
    }

    public void setContent(ArkActiveTransactionContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.ARK_ACTIVE_TRANSACTION;
    }
}
