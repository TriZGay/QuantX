package io.futakotome.trade.dto.message;

public class InstitutionHolderItem {
    private String periodText;               // 报告期，如 "2025/Q3"
    private Long institutionQuantity;       // 机构持股机构数量（家）
    private Long institutionQuantityChange; // 机构持股机构数量变化
    private Long holderQuantity;            // 机构持股总股数（股）
    private Long holderQuantityChange;      // 机构持股总股数变化
    private Double holderPct;                // 持股比例（%），如 12.34 表示 12.34%
    private Double holderPctChange;          // 持股比例变动（%），如 12.34 表示变动 12.34%

    public String getPeriodText() {
        return periodText;
    }

    public void setPeriodText(String periodText) {
        this.periodText = periodText;
    }

    public Long getInstitutionQuantity() {
        return institutionQuantity;
    }

    public void setInstitutionQuantity(Long institutionQuantity) {
        this.institutionQuantity = institutionQuantity;
    }

    public Long getInstitutionQuantityChange() {
        return institutionQuantityChange;
    }

    public void setInstitutionQuantityChange(Long institutionQuantityChange) {
        this.institutionQuantityChange = institutionQuantityChange;
    }

    public Long getHolderQuantity() {
        return holderQuantity;
    }

    public void setHolderQuantity(Long holderQuantity) {
        this.holderQuantity = holderQuantity;
    }

    public Long getHolderQuantityChange() {
        return holderQuantityChange;
    }

    public void setHolderQuantityChange(Long holderQuantityChange) {
        this.holderQuantityChange = holderQuantityChange;
    }

    public Double getHolderPct() {
        return holderPct;
    }

    public void setHolderPct(Double holderPct) {
        this.holderPct = holderPct;
    }

    public Double getHolderPctChange() {
        return holderPctChange;
    }

    public void setHolderPctChange(Double holderPctChange) {
        this.holderPctChange = holderPctChange;
    }
}
