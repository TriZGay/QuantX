package io.futakotome.trade.dto.message;

public class Indicator {
    private Integer indicatorType;
    private Double min;
    private Double max;

    public void setIndicatorType(Integer indicatorType) {
        this.indicatorType = indicatorType;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Integer getIndicatorType() {
        return indicatorType;
    }
}
