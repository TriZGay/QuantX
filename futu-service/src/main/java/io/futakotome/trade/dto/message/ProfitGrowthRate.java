package io.futakotome.trade.dto.message;

import java.util.List;

public class ProfitGrowthRate {
    private Double financialTtmMultiple; // TTM 增长倍数
    private Double marketCapMultiple; // 市值增长倍数
    private Integer yearCount; // 计算增长倍数时实际用到的年份数量
    private List<ProfitGrowthItem> profitData; // 各期数据
    private String conclusionDetailed; // 估值结论描述（已多语言翻译）

    public Double getFinancialTtmMultiple() {
        return financialTtmMultiple;
    }

    public void setFinancialTtmMultiple(Double financialTtmMultiple) {
        this.financialTtmMultiple = financialTtmMultiple;
    }

    public Double getMarketCapMultiple() {
        return marketCapMultiple;
    }

    public void setMarketCapMultiple(Double marketCapMultiple) {
        this.marketCapMultiple = marketCapMultiple;
    }

    public Integer getYearCount() {
        return yearCount;
    }

    public void setYearCount(Integer yearCount) {
        this.yearCount = yearCount;
    }

    public List<ProfitGrowthItem> getProfitData() {
        return profitData;
    }

    public void setProfitData(List<ProfitGrowthItem> profitData) {
        this.profitData = profitData;
    }

    public String getConclusionDetailed() {
        return conclusionDetailed;
    }

    public void setConclusionDetailed(String conclusionDetailed) {
        this.conclusionDetailed = conclusionDetailed;
    }
}
