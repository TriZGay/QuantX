package io.futakotome.trade.dto.message;

public class DividendRankItem {
    private CommonSecurity security;  // 股票
    private String name;                    // 名称
    private String industry;               // 行业

    // 行情
    private Double curPrice;              // 最新价
    private Double changeRate;            // 今日涨跌幅 (%)
    private Double changeAmount;          // 今日涨跌额
    private Double marketCap;             // 市值

    // 股息
    private Double dividendYieldTTM;      // 股息率TTM (%)
    private Double avgDividendYield5Y;    // 5年平均股息率 (%)
    private Integer distributionFrequency;  // 派息频率 (1:年派, 2:半年派, 3:季派, 4:月派)，不支持HK市场
    private Integer dividendGrowYear;       // 股息连续增长年数
    private Double dividendsTTM;          // 股息TTM (金额)
    private Double payoutRatioLFY;        // 股息支付率LFY (%)
    private String nextPayableDate;       // 下次派息日 ("YYYY-MM-DD")

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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Double getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(Double curPrice) {
        this.curPrice = curPrice;
    }

    public Double getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(Double changeRate) {
        this.changeRate = changeRate;
    }

    public Double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getDividendYieldTTM() {
        return dividendYieldTTM;
    }

    public void setDividendYieldTTM(Double dividendYieldTTM) {
        this.dividendYieldTTM = dividendYieldTTM;
    }

    public Double getAvgDividendYield5Y() {
        return avgDividendYield5Y;
    }

    public void setAvgDividendYield5Y(Double avgDividendYield5Y) {
        this.avgDividendYield5Y = avgDividendYield5Y;
    }

    public Integer getDistributionFrequency() {
        return distributionFrequency;
    }

    public void setDistributionFrequency(Integer distributionFrequency) {
        this.distributionFrequency = distributionFrequency;
    }

    public Integer getDividendGrowYear() {
        return dividendGrowYear;
    }

    public void setDividendGrowYear(Integer dividendGrowYear) {
        this.dividendGrowYear = dividendGrowYear;
    }

    public Double getDividendsTTM() {
        return dividendsTTM;
    }

    public void setDividendsTTM(Double dividendsTTM) {
        this.dividendsTTM = dividendsTTM;
    }

    public Double getPayoutRatioLFY() {
        return payoutRatioLFY;
    }

    public void setPayoutRatioLFY(Double payoutRatioLFY) {
        this.payoutRatioLFY = payoutRatioLFY;
    }

    public String getNextPayableDate() {
        return nextPayableDate;
    }

    public void setNextPayableDate(String nextPayableDate) {
        this.nextPayableDate = nextPayableDate;
    }
}
