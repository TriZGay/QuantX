package io.futakotome.trade.dto.message;

public class AnalystInfo {
    private String analystUid;
    private String analystName;   // 分析师姓名
    private String analystPictureUrl;   // 分析师头像 URL
    private Double numOfStars;   // 星级（0.0~5.0，如 3.50 表示 3.5 星）
    private Double successRate;   // 成功率，百分号前的值，如 12.34 表示 12.34%
    private Double excessReturn;   // 超额收益，百分号前的值，如 12.34 表示 12.34%
    private Double stockSuccessRate;   // 个股成功率，百分号前的值，如 12.34 表示 12.34%
    private Double stockAvgReturn;   // 个股平均收益，百分号前的值，如 12.34 表示 12.34%
    private InstInfo institutionInfo;   // 所属机构信息
    private Long updateTime;  // 更新时间戳（秒）
    private String updateTimeStr;  // 更新时间字符串，格式 YYYY-MM-DD，对应市场时区

    public String getAnalystUid() {
        return analystUid;
    }

    public void setAnalystUid(String analystUid) {
        this.analystUid = analystUid;
    }

    public String getAnalystName() {
        return analystName;
    }

    public void setAnalystName(String analystName) {
        this.analystName = analystName;
    }

    public String getAnalystPictureUrl() {
        return analystPictureUrl;
    }

    public void setAnalystPictureUrl(String analystPictureUrl) {
        this.analystPictureUrl = analystPictureUrl;
    }

    public Double getNumOfStars() {
        return numOfStars;
    }

    public void setNumOfStars(Double numOfStars) {
        this.numOfStars = numOfStars;
    }

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }

    public Double getExcessReturn() {
        return excessReturn;
    }

    public void setExcessReturn(Double excessReturn) {
        this.excessReturn = excessReturn;
    }

    public Double getStockSuccessRate() {
        return stockSuccessRate;
    }

    public void setStockSuccessRate(Double stockSuccessRate) {
        this.stockSuccessRate = stockSuccessRate;
    }

    public Double getStockAvgReturn() {
        return stockAvgReturn;
    }

    public void setStockAvgReturn(Double stockAvgReturn) {
        this.stockAvgReturn = stockAvgReturn;
    }

    public InstInfo getInstitutionInfo() {
        return institutionInfo;
    }

    public void setInstitutionInfo(InstInfo institutionInfo) {
        this.institutionInfo = institutionInfo;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}
