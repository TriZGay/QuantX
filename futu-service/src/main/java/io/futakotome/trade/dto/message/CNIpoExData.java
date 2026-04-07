package io.futakotome.trade.dto.message;

import java.util.List;

public class CNIpoExData {
    private String applyCode; // 申购代码
    private Long issueSize; // 发行总数
    private Long onlineIssueSize; // 网上发行量
    private Long applyUpperLimit; // 申购上限
    private Long applyLimitMarketValue; // 顶格申购需配市值
    private Boolean isEstimateIpoPrice; // 是否预估发行价
    private Double ipoPrice; // 发行价 预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    private Double industryPeRate; // 行业市盈率
    private Boolean isEstimateWinningRatio; // 是否预估中签率
    private Double winningRatio; // 中签率 该字段为百分比字段，默认不展示 %，如 20 实际对应 20%。预估值会因为募集资金、发行数量、发行费用等数据变动而变动，仅供参考。实际数据公布后会第一时间更新。
    private Double issuePeRate;  // 发行市盈率
    private String applyTime; // 申购日期字符串（格式：yyyy-MM-dd）
    private Double applyTimestamp; // 申购日期时间戳
    private String winningTime; // 公布中签日期字符串（格式：yyyy-MM-dd）
    private Double winningTimestamp; // 公布中签日期时间戳
    private Boolean isHasWon; // 是否已经公布中签号
    private List<WinningNumData> winningNumData;

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public Long getIssueSize() {
        return issueSize;
    }

    public void setIssueSize(Long issueSize) {
        this.issueSize = issueSize;
    }

    public Long getOnlineIssueSize() {
        return onlineIssueSize;
    }

    public void setOnlineIssueSize(Long onlineIssueSize) {
        this.onlineIssueSize = onlineIssueSize;
    }

    public Long getApplyUpperLimit() {
        return applyUpperLimit;
    }

    public void setApplyUpperLimit(Long applyUpperLimit) {
        this.applyUpperLimit = applyUpperLimit;
    }

    public Long getApplyLimitMarketValue() {
        return applyLimitMarketValue;
    }

    public void setApplyLimitMarketValue(Long applyLimitMarketValue) {
        this.applyLimitMarketValue = applyLimitMarketValue;
    }

    public Boolean getEstimateIpoPrice() {
        return isEstimateIpoPrice;
    }

    public void setEstimateIpoPrice(Boolean estimateIpoPrice) {
        isEstimateIpoPrice = estimateIpoPrice;
    }

    public Double getIpoPrice() {
        return ipoPrice;
    }

    public void setIpoPrice(Double ipoPrice) {
        this.ipoPrice = ipoPrice;
    }

    public Double getIndustryPeRate() {
        return industryPeRate;
    }

    public void setIndustryPeRate(Double industryPeRate) {
        this.industryPeRate = industryPeRate;
    }

    public Boolean getEstimateWinningRatio() {
        return isEstimateWinningRatio;
    }

    public void setEstimateWinningRatio(Boolean estimateWinningRatio) {
        isEstimateWinningRatio = estimateWinningRatio;
    }

    public Double getWinningRatio() {
        return winningRatio;
    }

    public void setWinningRatio(Double winningRatio) {
        this.winningRatio = winningRatio;
    }

    public Double getIssuePeRate() {
        return issuePeRate;
    }

    public void setIssuePeRate(Double issuePeRate) {
        this.issuePeRate = issuePeRate;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public Double getApplyTimestamp() {
        return applyTimestamp;
    }

    public void setApplyTimestamp(Double applyTimestamp) {
        this.applyTimestamp = applyTimestamp;
    }

    public String getWinningTime() {
        return winningTime;
    }

    public void setWinningTime(String winningTime) {
        this.winningTime = winningTime;
    }

    public Double getWinningTimestamp() {
        return winningTimestamp;
    }

    public void setWinningTimestamp(Double winningTimestamp) {
        this.winningTimestamp = winningTimestamp;
    }

    public Boolean getHasWon() {
        return isHasWon;
    }

    public void setHasWon(Boolean hasWon) {
        isHasWon = hasWon;
    }

    public List<WinningNumData> getWinningNumData() {
        return winningNumData;
    }

    public void setWinningNumData(List<WinningNumData> winningNumData) {
        this.winningNumData = winningNumData;
    }
}
