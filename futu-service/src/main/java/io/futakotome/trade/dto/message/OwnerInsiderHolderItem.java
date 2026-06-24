package io.futakotome.trade.dto.message;

public class OwnerInsiderHolderItem {
    private Long holderId; // 股东id，可作为 GetInsiderTradeList 和 Qot_GetShareholdersHolderDetail 的入参
    private Long holderQuantity; // 总持股数
    private Double holderPct; // 持股比例，百分号前的值，如 12.34 表示 12.34%
    private String name; // 股东名称
    private String title; // 股东职位

    public Long getHolderId() {
        return holderId;
    }

    public void setHolderId(Long holderId) {
        this.holderId = holderId;
    }

    public Long getHolderQuantity() {
        return holderQuantity;
    }

    public void setHolderQuantity(Long holderQuantity) {
        this.holderQuantity = holderQuantity;
    }

    public Double getHolderPct() {
        return holderPct;
    }

    public void setHolderPct(Double holderPct) {
        this.holderPct = holderPct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
