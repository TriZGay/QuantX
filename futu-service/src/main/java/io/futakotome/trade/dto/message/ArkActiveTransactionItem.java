package io.futakotome.trade.dto.message;

public class ArkActiveTransactionItem {
    private CommonSecurity security;  // 股票
    private String name;                    // 名称
    private Double changeAmount;            // 变动金额(美元)
    private Long changeShares;             // 变动数量(股)

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

    public Double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public Long getChangeShares() {
        return changeShares;
    }

    public void setChangeShares(Long changeShares) {
        this.changeShares = changeShares;
    }
}
