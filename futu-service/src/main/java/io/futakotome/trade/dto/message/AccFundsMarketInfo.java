package io.futakotome.trade.dto.message;

public class AccFundsMarketInfo {
    private Integer trdMarket;        // 交易市场, 参见TrdMarket的枚举定义
    private Double assets;          // 分市场资产信息

    public Integer getTrdMarket() {
        return trdMarket;
    }

    public void setTrdMarket(Integer trdMarket) {
        this.trdMarket = trdMarket;
    }

    public Double getAssets() {
        return assets;
    }

    public void setAssets(Double assets) {
        this.assets = assets;
    }
}
