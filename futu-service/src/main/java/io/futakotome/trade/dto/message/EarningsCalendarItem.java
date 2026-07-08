package io.futakotome.trade.dto.message;

import java.util.List;

public class EarningsCalendarItem {
    private CommonSecurity security ;   // 股票
    private String name ;                     // 股票名称
    private String earningsDate ;             // 财报日期("yyyy-MM-dd")
    private Double earningsTimestamp ;        // 财报发布时间戳(秒)
    private Integer pubType ;                   // EarningsCalendarPubType
    private String periodText ;               // 财年周期(如"2025Q1")
    private List<EstimateData> estimateList ;       // 预测数据列表
    // 期权字段(仅港美股)
    private Long optionVolume ;             // 期权成交量
    private Double iv ;                      // 隐含波动率(%)
    private Double ivRank ;                  // IV等级(%)
    private Double ivPercentile ;            // IV百分位数(%)
    // 行情字段
    private Double marketCap ;               // 实时市值
    private Double price ;                   // 最新价

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

    public String getEarningsDate() {
        return earningsDate;
    }

    public void setEarningsDate(String earningsDate) {
        this.earningsDate = earningsDate;
    }

    public Double getEarningsTimestamp() {
        return earningsTimestamp;
    }

    public void setEarningsTimestamp(Double earningsTimestamp) {
        this.earningsTimestamp = earningsTimestamp;
    }

    public Integer getPubType() {
        return pubType;
    }

    public void setPubType(Integer pubType) {
        this.pubType = pubType;
    }

    public String getPeriodText() {
        return periodText;
    }

    public void setPeriodText(String periodText) {
        this.periodText = periodText;
    }

    public List<EstimateData> getEstimateList() {
        return estimateList;
    }

    public void setEstimateList(List<EstimateData> estimateList) {
        this.estimateList = estimateList;
    }

    public Long getOptionVolume() {
        return optionVolume;
    }

    public void setOptionVolume(Long optionVolume) {
        this.optionVolume = optionVolume;
    }

    public Double getIv() {
        return iv;
    }

    public void setIv(Double iv) {
        this.iv = iv;
    }

    public Double getIvRank() {
        return ivRank;
    }

    public void setIvRank(Double ivRank) {
        this.ivRank = ivRank;
    }

    public Double getIvPercentile() {
        return ivPercentile;
    }

    public void setIvPercentile(Double ivPercentile) {
        this.ivPercentile = ivPercentile;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
