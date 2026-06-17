package io.futakotome.trade.dto.message;

import java.util.List;

public class ValuationTrend {
    private Double currentValue; // 当前估值
    private Double averageValue; // 历史平均估值
    private Double avgMinus1Stddev; // 历史平均 - 1σ
    private Double avgPlus1Stddev; // 历史平均 + 1σ
    private Double valuationPercentile; // 估值历史分位，百分号前的值，如 12.34 表示 12.34%
    private Double forwardValue; // 预测估值，仅 PE / PS 有
    private List<ValuationHistoricalItem> historicalItems; // 历史数据

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }

    public Double getAvgMinus1Stddev() {
        return avgMinus1Stddev;
    }

    public void setAvgMinus1Stddev(Double avgMinus1Stddev) {
        this.avgMinus1Stddev = avgMinus1Stddev;
    }

    public Double getAvgPlus1Stddev() {
        return avgPlus1Stddev;
    }

    public void setAvgPlus1Stddev(Double avgPlus1Stddev) {
        this.avgPlus1Stddev = avgPlus1Stddev;
    }

    public Double getValuationPercentile() {
        return valuationPercentile;
    }

    public void setValuationPercentile(Double valuationPercentile) {
        this.valuationPercentile = valuationPercentile;
    }

    public Double getForwardValue() {
        return forwardValue;
    }

    public void setForwardValue(Double forwardValue) {
        this.forwardValue = forwardValue;
    }

    public List<ValuationHistoricalItem> getHistoricalItems() {
        return historicalItems;
    }

    public void setHistoricalItems(List<ValuationHistoricalItem> historicalItems) {
        this.historicalItems = historicalItems;
    }
}
