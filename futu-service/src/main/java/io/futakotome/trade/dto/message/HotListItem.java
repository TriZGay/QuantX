package io.futakotome.trade.dto.message;

public class HotListItem {
    private CommonSecurity security;  // 股票
    private String name;                    // 名称

    private Double tradeHeat;             // 交易热度
    private Double tradeHeatChange;       // 交易热度变化
    private Double searchHeat;            // 搜索热度
    private Double searchHeatChange;    // 搜索热度变化
    private Double newsHeat;              // 资讯热度
    private Double newsHeatChange;        // 资讯热度变化
    private Double averageHeat;           // 综合热度
    private Double averageHeatChange;     // 综合热度变化
    private HotNewsInfo newsInfo;         // 关联新闻

    public CommonSecurity getSecurity() {
        return security;
    }

    public void setSecurity(CommonSecurity security) {
        this.security = security;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTradeHeat() {
        return tradeHeat;
    }

    public void setTradeHeat(Double tradeHeat) {
        this.tradeHeat = tradeHeat;
    }

    public Double getTradeHeatChange() {
        return tradeHeatChange;
    }

    public void setTradeHeatChange(Double tradeHeatChange) {
        this.tradeHeatChange = tradeHeatChange;
    }

    public Double getSearchHeat() {
        return searchHeat;
    }

    public void setSearchHeat(Double searchHeat) {
        this.searchHeat = searchHeat;
    }

    public Double getSearchHeatChange() {
        return searchHeatChange;
    }

    public void setSearchHeatChange(Double searchHeatChange) {
        this.searchHeatChange = searchHeatChange;
    }

    public Double getNewsHeat() {
        return newsHeat;
    }

    public void setNewsHeat(Double newsHeat) {
        this.newsHeat = newsHeat;
    }

    public Double getNewsHeatChange() {
        return newsHeatChange;
    }

    public void setNewsHeatChange(Double newsHeatChange) {
        this.newsHeatChange = newsHeatChange;
    }

    public Double getAverageHeat() {
        return averageHeat;
    }

    public void setAverageHeat(Double averageHeat) {
        this.averageHeat = averageHeat;
    }

    public Double getAverageHeatChange() {
        return averageHeatChange;
    }

    public void setAverageHeatChange(Double averageHeatChange) {
        this.averageHeatChange = averageHeatChange;
    }

    public HotNewsInfo getNewsInfo() {
        return newsInfo;
    }

    public void setNewsInfo(HotNewsInfo newsInfo) {
        this.newsInfo = newsInfo;
    }
}
