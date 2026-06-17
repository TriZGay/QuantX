package io.futakotome.trade.dto.message;

import java.util.List;

public class MarketDistribution {
    private List<DistributionSection> sections; // 区间分布（降序）
    private Integer total; // 市场总数 / 成分股总数
    private Integer ranking; // 该股票估值在市场中的排名（指数无）
    private Double averageValue; // 市场估值均值（指数无）
    private Double medianValue; // 市场估值中位数（指数无）

    public List<DistributionSection> getSections() {
        return sections;
    }

    public void setSections(List<DistributionSection> sections) {
        this.sections = sections;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }

    public Double getMedianValue() {
        return medianValue;
    }

    public void setMedianValue(Double medianValue) {
        this.medianValue = medianValue;
    }
}
