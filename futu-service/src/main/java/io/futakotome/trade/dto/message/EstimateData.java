package io.futakotome.trade.dto.message;

public class EstimateData {
    private Integer estimateType;     // EarningsCalendarEstimateType
    private Double actualValue;     // 实际值(已发布时有值)
    private Double predictValue;    // 预测值
    private String currency;        // 货币单位(ISO 4217)
    private Integer periodType;       // EarningsCalendarPeriodType

    public Integer getEstimateType() {
        return estimateType;
    }

    public void setEstimateType(Integer estimateType) {
        this.estimateType = estimateType;
    }

    public Double getActualValue() {
        return actualValue;
    }

    public void setActualValue(Double actualValue) {
        this.actualValue = actualValue;
    }

    public Double getPredictValue() {
        return predictValue;
    }

    public void setPredictValue(Double predictValue) {
        this.predictValue = predictValue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getPeriodType() {
        return periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }
}
