package io.futakotome.trade.dto.message;

public class OwnershipStaticItem {
    private String name; // 持股人/分组名称
    private Double holderPct; // 持股占比（百分比），如 23.37 表示 23.37%
    private Integer holderId; // 股东 ID；mainHolderInfoList 中有值，其他分组为 0

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHolderPct() {
        return holderPct;
    }

    public void setHolderPct(Double holderPct) {
        this.holderPct = holderPct;
    }

    public Integer getHolderId() {
        return holderId;
    }

    public void setHolderId(Integer holderId) {
        this.holderId = holderId;
    }
}
