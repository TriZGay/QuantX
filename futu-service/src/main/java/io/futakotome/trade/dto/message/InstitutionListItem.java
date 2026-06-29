package io.futakotome.trade.dto.message;

public class InstitutionListItem {
    private Integer institutionId;       // 机构ID
    private String institutionName;    // 机构名称
    private Double positionValue;      // 持仓市值
    private Double positionValueChange;// 持仓市值变化
    private Integer positionCount;       // 持仓股票数
    private Integer positionCountChange; // 持仓股票数变化
    private String disclosureDate;     // 披露日期(yyyy-MM-dd)

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public Double getPositionValue() {
        return positionValue;
    }

    public void setPositionValue(Double positionValue) {
        this.positionValue = positionValue;
    }

    public Double getPositionValueChange() {
        return positionValueChange;
    }

    public void setPositionValueChange(Double positionValueChange) {
        this.positionValueChange = positionValueChange;
    }

    public Integer getPositionCount() {
        return positionCount;
    }

    public void setPositionCount(Integer positionCount) {
        this.positionCount = positionCount;
    }

    public Integer getPositionCountChange() {
        return positionCountChange;
    }

    public void setPositionCountChange(Integer positionCountChange) {
        this.positionCountChange = positionCountChange;
    }

    public String getDisclosureDate() {
        return disclosureDate;
    }

    public void setDisclosureDate(String disclosureDate) {
        this.disclosureDate = disclosureDate;
    }
}
