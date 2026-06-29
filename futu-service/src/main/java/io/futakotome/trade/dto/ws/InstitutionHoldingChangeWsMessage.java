package io.futakotome.trade.dto.ws;

import io.futakotome.trade.dto.message.InstitutionHoldingChangeContent;

public class InstitutionHoldingChangeWsMessage implements Message {
    private Integer market;              // Qot_Common.QotMarket
    private Integer institutionId;       // 机构ID
    private Integer changeType;          // HoldingChangeType, 默认建仓
    private Integer sortField;           // SortField
    private Integer sortDir;             // SortDir
    private Integer count;               // 条数 [1,200], 默认20
    private String page;               // 翻页游标, 首次不传
    private InstitutionHoldingChangeContent content;

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
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

    public InstitutionHoldingChangeContent getContent() {
        return content;
    }

    public void setContent(InstitutionHoldingChangeContent content) {
        this.content = content;
    }

    @Override
    public MessageType getType() {
        return MessageType.INSTITUTION_HOLDING_CHANGE;
    }
}
