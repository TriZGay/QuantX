package io.futakotome.trade.dto.message;

public class OwnerInsiderTradeItem {
    private Long tradeShares;  // 交易股数
    private Long minTradeDate;  // 最小交易日期时间戳（秒）
    private String minTradeDateStr;  // 最小交易日期字符串，格式 YYYY-MM-DD，对应市场时区
    private Long maxTradeDate;  // 最大交易日期时间戳（秒）
    private String maxTradeDateStr;  // 最大交易日期字符串，格式 YYYY-MM-DD，对应市场时区
    private Double minPrice;  // 最小交易价格
    private Double maxPrice;  // 最大交易价格
    private Long securityHolderQuantity;  // 证券类型持股数
    private Boolean isProposedSaleOfSecurities;  // 是否为计划出售证券
    private Long holderId; // 股东id
    private String name; // 股东名称
    private String title; // 股东职位
    private String securityDescription;  // 证券类型描述
    private String transactionType; // 交易类型
    private String sourceGroupName; // 交易数据来源

    public Long getTradeShares() {
        return tradeShares;
    }

    public void setTradeShares(Long tradeShares) {
        this.tradeShares = tradeShares;
    }

    public Long getMinTradeDate() {
        return minTradeDate;
    }

    public void setMinTradeDate(Long minTradeDate) {
        this.minTradeDate = minTradeDate;
    }

    public String getMinTradeDateStr() {
        return minTradeDateStr;
    }

    public void setMinTradeDateStr(String minTradeDateStr) {
        this.minTradeDateStr = minTradeDateStr;
    }

    public Long getMaxTradeDate() {
        return maxTradeDate;
    }

    public void setMaxTradeDate(Long maxTradeDate) {
        this.maxTradeDate = maxTradeDate;
    }

    public String getMaxTradeDateStr() {
        return maxTradeDateStr;
    }

    public void setMaxTradeDateStr(String maxTradeDateStr) {
        this.maxTradeDateStr = maxTradeDateStr;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Long getSecurityHolderQuantity() {
        return securityHolderQuantity;
    }

    public void setSecurityHolderQuantity(Long securityHolderQuantity) {
        this.securityHolderQuantity = securityHolderQuantity;
    }

    public Boolean getProposedSaleOfSecurities() {
        return isProposedSaleOfSecurities;
    }

    public void setProposedSaleOfSecurities(Boolean proposedSaleOfSecurities) {
        isProposedSaleOfSecurities = proposedSaleOfSecurities;
    }

    public Long getHolderId() {
        return holderId;
    }

    public void setHolderId(Long holderId) {
        this.holderId = holderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSecurityDescription() {
        return securityDescription;
    }

    public void setSecurityDescription(String securityDescription) {
        this.securityDescription = securityDescription;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getSourceGroupName() {
        return sourceGroupName;
    }

    public void setSourceGroupName(String sourceGroupName) {
        this.sourceGroupName = sourceGroupName;
    }
}
