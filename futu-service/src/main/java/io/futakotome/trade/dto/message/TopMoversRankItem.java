package io.futakotome.trade.dto.message;

public class TopMoversRankItem {
    private CommonSecurity security;  // 股票
    private String name;                    // 名称

    private Double curPrice;             // 最新价
    private Double changeRatio;          // 涨跌幅(%)
    private Double changeAmount;         // 涨跌额
    private Double turnover;             // 成交额
    private Long volume;                // 成交量
    private Double turnoverRatio;        // 换手率(%)
    private Double peTTM;                // 市盈率TTM
    private Double amplitude;            // 振幅(%)
    private Double marketCap;            // 市值
    private Double volumeRatio;          // 量比

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

    public Double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(Double curPrice) {
        this.curPrice = curPrice;
    }

    public Double getChangeRatio() {
        return changeRatio;
    }

    public void setChangeRatio(Double changeRatio) {
        this.changeRatio = changeRatio;
    }

    public Double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Double getTurnoverRatio() {
        return turnoverRatio;
    }

    public void setTurnoverRatio(Double turnoverRatio) {
        this.turnoverRatio = turnoverRatio;
    }

    public Double getPeTTM() {
        return peTTM;
    }

    public void setPeTTM(Double peTTM) {
        this.peTTM = peTTM;
    }

    public Double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(Double amplitude) {
        this.amplitude = amplitude;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getVolumeRatio() {
        return volumeRatio;
    }

    public void setVolumeRatio(Double volumeRatio) {
        this.volumeRatio = volumeRatio;
    }
}
