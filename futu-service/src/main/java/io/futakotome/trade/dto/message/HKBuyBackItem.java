package io.futakotome.trade.dto.message;

public class HKBuyBackItem {
    private Long publDate;  // 公告日时间戳（秒）
    private String publDateStr;  // 公告日字符串，格式 YYYY-MM-DD，对应市场时区
    private Long endDate;  // 回购截止日时间戳（秒）
    private String endDateStr;  // 回购截止日字符串，格式 YYYY-MM-DD，对应市场时区
    private Double buyBackMoney;  // 回购金额
    private Long buyBackSum;  // 回购股数（股）
    private Double percentage;  // 占已发行股份百分比，百分号前的值，如 12.34 表示 12.34%
    private Double highPrice;  // 最高回购价
    private Double lowPrice;  // 最低回购价
    private Long cumulativeSum; // 本轮累计回购股数（股）
    private Double cumulativePercentage; // 本轮累计回购占总股本百分比，百分号前的值，如 12.34 表示 12.34%
    private String shareType; // 股份类别

    public Long getPublDate() {
        return publDate;
    }

    public void setPublDate(Long publDate) {
        this.publDate = publDate;
    }

    public String getPublDateStr() {
        return publDateStr;
    }

    public void setPublDateStr(String publDateStr) {
        this.publDateStr = publDateStr;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Double getBuyBackMoney() {
        return buyBackMoney;
    }

    public void setBuyBackMoney(Double buyBackMoney) {
        this.buyBackMoney = buyBackMoney;
    }

    public Long getBuyBackSum() {
        return buyBackSum;
    }

    public void setBuyBackSum(Long buyBackSum) {
        this.buyBackSum = buyBackSum;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Long getCumulativeSum() {
        return cumulativeSum;
    }

    public void setCumulativeSum(Long cumulativeSum) {
        this.cumulativeSum = cumulativeSum;
    }

    public Double getCumulativePercentage() {
        return cumulativePercentage;
    }

    public void setCumulativePercentage(Double cumulativePercentage) {
        this.cumulativePercentage = cumulativePercentage;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }
}
