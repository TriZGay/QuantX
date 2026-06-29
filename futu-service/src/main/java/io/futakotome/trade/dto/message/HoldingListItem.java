package io.futakotome.trade.dto.message;

public class HoldingListItem {
    private CommonSecurity security;  // 股票
    private String name;                    // 名称
    private String industryName;            // 所属行业
    private Double holdingValue;            // 持股市值
    private Double holdingPct;              // 持股比例-占股票总市值(%)
    private Double lastHoldingPct;          // 上期持股比例(%)
    private Long changeShares;             // 变动股数
    private Double portfolioPct;            // 占机构总仓位比例(%)
    private Double changePct;               // 变动比例(%)
    private String holdingDate;            // 持仓时间(yyyy-MM-dd)
    private String source;                 // 披露来源

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

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public Double getHoldingValue() {
        return holdingValue;
    }

    public void setHoldingValue(Double holdingValue) {
        this.holdingValue = holdingValue;
    }

    public Double getHoldingPct() {
        return holdingPct;
    }

    public void setHoldingPct(Double holdingPct) {
        this.holdingPct = holdingPct;
    }

    public Double getLastHoldingPct() {
        return lastHoldingPct;
    }

    public void setLastHoldingPct(Double lastHoldingPct) {
        this.lastHoldingPct = lastHoldingPct;
    }

    public Long getChangeShares() {
        return changeShares;
    }

    public void setChangeShares(Long changeShares) {
        this.changeShares = changeShares;
    }

    public Double getPortfolioPct() {
        return portfolioPct;
    }

    public void setPortfolioPct(Double portfolioPct) {
        this.portfolioPct = portfolioPct;
    }

    public Double getChangePct() {
        return changePct;
    }

    public void setChangePct(Double changePct) {
        this.changePct = changePct;
    }

    public String getHoldingDate() {
        return holdingDate;
    }

    public void setHoldingDate(String holdingDate) {
        this.holdingDate = holdingDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
