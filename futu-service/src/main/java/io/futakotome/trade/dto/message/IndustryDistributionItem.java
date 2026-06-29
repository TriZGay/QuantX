package io.futakotome.trade.dto.message;

public class IndustryDistributionItem {
    private Long industryId;         // 行业ID
    private String industryName;       // 行业名称
    private Double positionValue;      // 持仓市值
    private Double portfolioPct;       // 行业占比(%)

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public Double getPositionValue() {
        return positionValue;
    }

    public void setPositionValue(Double positionValue) {
        this.positionValue = positionValue;
    }

    public Double getPortfolioPct() {
        return portfolioPct;
    }

    public void setPortfolioPct(Double portfolioPct) {
        this.portfolioPct = portfolioPct;
    }
}
