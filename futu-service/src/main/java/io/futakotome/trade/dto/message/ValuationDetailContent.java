package io.futakotome.trade.dto.message;

import com.google.gson.annotations.JsonAdapter;
import io.futakotome.trade.utils.converter.ValuationDetailContentConverter;

@JsonAdapter(ValuationDetailContentConverter.class)
public class ValuationDetailContent {
    private Integer valuationType; // 实际返回的估值类型，详见 Qot_Common.ValuationType 定义
    private String valuationTypeStr;
    private Long lastUpdateTime; // 最后更新时间戳（秒）
    private String lastUpdateTimeStr; // 最后更新时间字符串，格式 YYYY-MM-DD HH:MM:SS，对应市场时区
    private ValuationTrend trend; // 走势（个股 + 指数）
    private MarketDistribution marketDistribution; // 市场分布 / 成分股分布（个股 + 指数）
    private PlateDistribution plateDistribution; // 行业分布（仅个股）
    private ProfitGrowthRate profitGrowthRate; // 盈利 / 营收增速（仅个股，PB 无）

    public String getValuationTypeStr() {
        return valuationTypeStr;
    }

    public void setValuationTypeStr(String valuationTypeStr) {
        this.valuationTypeStr = valuationTypeStr;
    }

    public Integer getValuationType() {
        return valuationType;
    }

    public void setValuationType(Integer valuationType) {
        this.valuationType = valuationType;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateTimeStr() {
        return lastUpdateTimeStr;
    }

    public void setLastUpdateTimeStr(String lastUpdateTimeStr) {
        this.lastUpdateTimeStr = lastUpdateTimeStr;
    }

    public ValuationTrend getTrend() {
        return trend;
    }

    public void setTrend(ValuationTrend trend) {
        this.trend = trend;
    }

    public MarketDistribution getMarketDistribution() {
        return marketDistribution;
    }

    public void setMarketDistribution(MarketDistribution marketDistribution) {
        this.marketDistribution = marketDistribution;
    }

    public PlateDistribution getPlateDistribution() {
        return plateDistribution;
    }

    public void setPlateDistribution(PlateDistribution plateDistribution) {
        this.plateDistribution = plateDistribution;
    }

    public ProfitGrowthRate getProfitGrowthRate() {
        return profitGrowthRate;
    }

    public void setProfitGrowthRate(ProfitGrowthRate profitGrowthRate) {
        this.profitGrowthRate = profitGrowthRate;
    }
}
