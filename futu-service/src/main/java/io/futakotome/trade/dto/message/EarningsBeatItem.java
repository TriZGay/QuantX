package io.futakotome.trade.dto.message;

public class EarningsBeatItem {
    private CommonSecurity security;  // 股票
    private String name;                    // 名称
    private String industry;               // 行业

    // 行情
    private Double curPrice;              // 最新价
    private Double lastClosePrice;        // 昨收价
    private Double changeRate;            // 今日涨跌幅 (%)
    private Double marketCap;             // 市值
    private Double peTTM;                 // 市盈率TTM
    private Double dividendsTTM;          // 股息率TTM (%)

    // 盈利超预期
    private String releasedDate;          // 发布日期 (如 "2024-01-15")
    private Double beatRatio;             // 超预期比率 (%)
    private Double actual;                // 实际值
    private Double estimate;              // 预测值
    private Double yoy;                   // 去年同期
    private Double yoyGrowth;             // 同比增长率 (%)
    private Double earningDayChg;         // 财报后首日涨幅 (%)
    private String term;                  // 财报周期 (如 "2024/Q1")
    private Integer detailPostPeriod;       // PostPeriodType, 发布具体时段

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

    public Double getLastClosePrice() {
        return lastClosePrice;
    }

    public void setLastClosePrice(Double lastClosePrice) {
        this.lastClosePrice = lastClosePrice;
    }

    public Double getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(Double changeRate) {
        this.changeRate = changeRate;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getPeTTM() {
        return peTTM;
    }

    public void setPeTTM(Double peTTM) {
        this.peTTM = peTTM;
    }

    public Double getDividendsTTM() {
        return dividendsTTM;
    }

    public void setDividendsTTM(Double dividendsTTM) {
        this.dividendsTTM = dividendsTTM;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    public Double getBeatRatio() {
        return beatRatio;
    }

    public void setBeatRatio(Double beatRatio) {
        this.beatRatio = beatRatio;
    }

    public Double getActual() {
        return actual;
    }

    public void setActual(Double actual) {
        this.actual = actual;
    }

    public Double getEstimate() {
        return estimate;
    }

    public void setEstimate(Double estimate) {
        this.estimate = estimate;
    }

    public Double getYoy() {
        return yoy;
    }

    public void setYoy(Double yoy) {
        this.yoy = yoy;
    }

    public Double getYoyGrowth() {
        return yoyGrowth;
    }

    public void setYoyGrowth(Double yoyGrowth) {
        this.yoyGrowth = yoyGrowth;
    }

    public Double getEarningDayChg() {
        return earningDayChg;
    }

    public void setEarningDayChg(Double earningDayChg) {
        this.earningDayChg = earningDayChg;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getDetailPostPeriod() {
        return detailPostPeriod;
    }

    public void setDetailPostPeriod(Integer detailPostPeriod) {
        this.detailPostPeriod = detailPostPeriod;
    }
}
