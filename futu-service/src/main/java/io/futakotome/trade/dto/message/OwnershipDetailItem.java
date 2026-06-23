package io.futakotome.trade.dto.message;

public class OwnershipDetailItem {
    private String periodText;           // 报告期，如 "2025/Q3"
    private Long holderId;             // 持股人 ID
    private String name;                 // 股东名称
    private Long holderQuantity;        // 总持股数（股）
    private Long holderQuantityChange;  // 持股变动数（股，正为增持，负为减持）
    private Double holderPct;            // 持股比例（%），如 12.34 表示 12.34%
    private Double holderPctChange;      // 持股变动比例（%），如 12.34 表示 12.34%；负值为减少
    private Long holdingDate;          // 持股日期时间戳（秒）
    private String holdingDateStr;       // 持股日期字符串，格式 YYYY-MM-DD，香港时区
    private Double closePrice;          // 持股日期对应收盘价（真实价格）
    private Double priceChangePct;      // 持股日期对应涨跌幅（%），如 -0.4467 表示 -0.4467%
    private String sourceGroupName;     // 持股明细披露信息来源（如 13F、多份文件等）

    public String getPeriodText() {
        return periodText;
    }

    public void setPeriodText(String periodText) {
        this.periodText = periodText;
    }

    public Long getHolderId() {
        return holderId;
    }

    public void setHolderId(Long holderId) {
        this.holderId = holderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getHoldingDate() {
        return holdingDate;
    }

    public void setHoldingDate(Long holdingDate) {
        this.holdingDate = holdingDate;
    }

    public String getHoldingDateStr() {
        return holdingDateStr;
    }

    public void setHoldingDateStr(String holdingDateStr) {
        this.holdingDateStr = holdingDateStr;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getPriceChangePct() {
        return priceChangePct;
    }

    public void setPriceChangePct(Double priceChangePct) {
        this.priceChangePct = priceChangePct;
    }

    public String getSourceGroupName() {
        return sourceGroupName;
    }

    public void setSourceGroupName(String sourceGroupName) {
        this.sourceGroupName = sourceGroupName;
    }
}
