package io.futakotome.trade.dto.message;

public class ArkStockDynamicContent {
    private Integer dynamicType;              // DynamicType
    private Integer transactionCount;         // 交易次数
    private Integer netShares;                // 净交易股数
    private String lastTransactionTime;     // 最近交易时间(yyyy-MM-dd)

    public Integer getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(Integer dynamicType) {
        this.dynamicType = dynamicType;
    }

    public Integer getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(Integer transactionCount) {
        this.transactionCount = transactionCount;
    }

    public Integer getNetShares() {
        return netShares;
    }

    public void setNetShares(Integer netShares) {
        this.netShares = netShares;
    }

    public String getLastTransactionTime() {
        return lastTransactionTime;
    }

    public void setLastTransactionTime(String lastTransactionTime) {
        this.lastTransactionTime = lastTransactionTime;
    }
}
