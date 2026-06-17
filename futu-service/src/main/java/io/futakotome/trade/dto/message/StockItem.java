package io.futakotome.trade.dto.message;

public class StockItem {
    private CommonSecurity security;    // 股票
    private String name;    // 股票名称
    private Double valuationVal;    // 估值
    private Double forwardValue;    // 预测估值，当前仅支持 PE 和 PS
    private Double valuationPercentile;    // 估值历史分位，百分号前的值，如 12.34 表示 12.34%
    private Double marketCap;    // 市值

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

    public Double getValuationVal() {
        return valuationVal;
    }

    public void setValuationVal(Double valuationVal) {
        this.valuationVal = valuationVal;
    }

    public Double getForwardValue() {
        return forwardValue;
    }

    public void setForwardValue(Double forwardValue) {
        this.forwardValue = forwardValue;
    }

    public Double getValuationPercentile() {
        return valuationPercentile;
    }

    public void setValuationPercentile(Double valuationPercentile) {
        this.valuationPercentile = valuationPercentile;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }
}
