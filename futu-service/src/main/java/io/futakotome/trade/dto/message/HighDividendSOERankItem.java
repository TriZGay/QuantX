package io.futakotome.trade.dto.message;
// 默认固定条件(服务端硬编码): 概念板块=特估国企 + PB<=1 + 股息率TTM>=5% + PE>=0
public class HighDividendSOERankItem {
   private  CommonSecurity security ;  // 股票
   private  String name ;                    // 名称
   private  String industry ;               // 所属行业

   private  Double curPrice ;              // 最新价
   private  Double changeRatio ;           // 涨跌幅(%)
   private  Double turnover ;              // 成交额
   private  Long volume ;                 // 成交量
   private  Double marketCap ;             // 市值

   private  Double peTTM ;                 // 市盈率TTM
   private  Double pb ;                    // 市净率
   private  Double dividendYieldTTM ;      // 股息率TTM(%)
   private  Double turnoverRatio ;         // 换手率(%)

   private  Double changeRate5D ;          // 5日涨幅(%)
   private  Double changeRate10D ;         // 10日涨幅(%)
   private  Double changeRate20D ;         // 20日涨幅(%)
   private  Double changeRate60D ;         // 60日涨幅(%)
   private  Double changeRate120D ;        // 120日涨幅(%)
   private  Double changeRate250D ;        // 250日涨幅(%)

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

    public Double getChangeRatio() {
        return changeRatio;
    }

    public void setChangeRatio(Double changeRatio) {
        this.changeRatio = changeRatio;
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

    public Double getPb() {
        return pb;
    }

    public void setPb(Double pb) {
        this.pb = pb;
    }

    public Double getDividendYieldTTM() {
        return dividendYieldTTM;
    }

    public void setDividendYieldTTM(Double dividendYieldTTM) {
        this.dividendYieldTTM = dividendYieldTTM;
    }

    public Double getTurnoverRatio() {
        return turnoverRatio;
    }

    public void setTurnoverRatio(Double turnoverRatio) {
        this.turnoverRatio = turnoverRatio;
    }

    public Double getChangeRate5D() {
        return changeRate5D;
    }

    public void setChangeRate5D(Double changeRate5D) {
        this.changeRate5D = changeRate5D;
    }

    public Double getChangeRate10D() {
        return changeRate10D;
    }

    public void setChangeRate10D(Double changeRate10D) {
        this.changeRate10D = changeRate10D;
    }

    public Double getChangeRate20D() {
        return changeRate20D;
    }

    public void setChangeRate20D(Double changeRate20D) {
        this.changeRate20D = changeRate20D;
    }

    public Double getChangeRate60D() {
        return changeRate60D;
    }

    public void setChangeRate60D(Double changeRate60D) {
        this.changeRate60D = changeRate60D;
    }

    public Double getChangeRate120D() {
        return changeRate120D;
    }

    public void setChangeRate120D(Double changeRate120D) {
        this.changeRate120D = changeRate120D;
    }

    public Double getChangeRate250D() {
        return changeRate250D;
    }

    public void setChangeRate250D(Double changeRate250D) {
        this.changeRate250D = changeRate250D;
    }
}
