package io.futakotome.trade.dto.message;

public class InstitutionProfileContent {
    private String institutionName;    // 机构名称
    private String description;        // 机构简介
    private Double positionValue;      // 持仓市值
    private Double lastPositionValue;  // 上期持仓市值
    private Double positionValueChangePct; // 市值变化比例(%)
    private Long totalHoldingCount;   // 总持仓数
    private Long holdingChangeCount;  // 持仓变动数
    private Integer newCount;            // 建仓标的数
    private Integer soldOutCount;        // 清仓标的数
    private Integer increaseCount;      // 增持标的数
    private Integer decreaseCount;      // 减持标的数
    private Double top10Pct;          // Top10持股占比(%)
    private Double top10PctChange;    // Top10占比变动(%)
    private String disclosureDate;    // 披露日期(yyyy-MM-dd)
    private String currency;          // 币种

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPositionValue() {
        return positionValue;
    }

    public void setPositionValue(Double positionValue) {
        this.positionValue = positionValue;
    }

    public Double getLastPositionValue() {
        return lastPositionValue;
    }

    public void setLastPositionValue(Double lastPositionValue) {
        this.lastPositionValue = lastPositionValue;
    }

    public Double getPositionValueChangePct() {
        return positionValueChangePct;
    }

    public void setPositionValueChangePct(Double positionValueChangePct) {
        this.positionValueChangePct = positionValueChangePct;
    }

    public Long getTotalHoldingCount() {
        return totalHoldingCount;
    }

    public void setTotalHoldingCount(Long totalHoldingCount) {
        this.totalHoldingCount = totalHoldingCount;
    }

    public Long getHoldingChangeCount() {
        return holdingChangeCount;
    }

    public void setHoldingChangeCount(Long holdingChangeCount) {
        this.holdingChangeCount = holdingChangeCount;
    }

    public Integer getNewCount() {
        return newCount;
    }

    public void setNewCount(Integer newCount) {
        this.newCount = newCount;
    }

    public Integer getSoldOutCount() {
        return soldOutCount;
    }

    public void setSoldOutCount(Integer soldOutCount) {
        this.soldOutCount = soldOutCount;
    }

    public Integer getIncreaseCount() {
        return increaseCount;
    }

    public void setIncreaseCount(Integer increaseCount) {
        this.increaseCount = increaseCount;
    }

    public Integer getDecreaseCount() {
        return decreaseCount;
    }

    public void setDecreaseCount(Integer decreaseCount) {
        this.decreaseCount = decreaseCount;
    }

    public Double getTop10Pct() {
        return top10Pct;
    }

    public void setTop10Pct(Double top10Pct) {
        this.top10Pct = top10Pct;
    }

    public Double getTop10PctChange() {
        return top10PctChange;
    }

    public void setTop10PctChange(Double top10PctChange) {
        this.top10PctChange = top10PctChange;
    }

    public String getDisclosureDate() {
        return disclosureDate;
    }

    public void setDisclosureDate(String disclosureDate) {
        this.disclosureDate = disclosureDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
