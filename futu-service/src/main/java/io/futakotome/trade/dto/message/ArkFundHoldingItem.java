package io.futakotome.trade.dto.message;

public class ArkFundHoldingItem {
    private CommonSecurity security;  // 股票(stock_id有效时)
    private String name;                    // 名称
    private Long shares;                   // 持仓数量
    private Long sharesChange;             // 持仓数量变动
    private Double marketValue;             // 持仓市值(美元)
    private Double weight;                  // 持仓占比(%)
    private Double weightChange;            // 持仓占比变动(%)

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

    public Long getShares() {
        return shares;
    }

    public void setShares(Long shares) {
        this.shares = shares;
    }

    public Long getSharesChange() {
        return sharesChange;
    }

    public void setSharesChange(Long sharesChange) {
        this.sharesChange = sharesChange;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWeightChange() {
        return weightChange;
    }

    public void setWeightChange(Double weightChange) {
        this.weightChange = weightChange;
    }
}
