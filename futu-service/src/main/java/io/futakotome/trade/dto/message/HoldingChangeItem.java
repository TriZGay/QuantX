package io.futakotome.trade.dto.message;

public class HoldingChangeItem {
    private CommonSecurity security;  // 股票
    private String name;                    // 名称
    private Double portfolioPct;            // 持股比例(%)
    private Long changeShares;             // 变动股数
    private Double changePct;               // 变动比例(%)
    private String holdingDate;             // 持仓时间(yyyy-MM-dd)
    private String source;                  // 披露来源

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

    public Double getPortfolioPct() {
        return portfolioPct;
    }

    public void setPortfolioPct(Double portfolioPct) {
        this.portfolioPct = portfolioPct;
    }

    public Long getChangeShares() {
        return changeShares;
    }

    public void setChangeShares(Long changeShares) {
        this.changeShares = changeShares;
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
