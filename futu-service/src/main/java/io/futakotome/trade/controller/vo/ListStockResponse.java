package io.futakotome.trade.controller.vo;

public class ListStockResponse {
    private Long id;
    private String name;
    private String code;
    private Integer lotSize;
    private String stockType;
    private String stockChildType;
    private String stockOwner;
    private String optionType;
    private String strikeTime;
    private Double strikePrice;
    private String suspension;
    private String listingDate;
    private String stockId;
    private String delisting;
    private String indexOptionType;
    private String mainContract;
    private String lastTradeTime;
    private String exchangeType;
    private String market;
    private Integer marketCode;
    private Integer stockTypeCode;

    public Integer getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(Integer marketCode) {
        this.marketCode = marketCode;
    }

    public Integer getStockTypeCode() {
        return stockTypeCode;
    }

    public void setStockTypeCode(Integer stockTypeCode) {
        this.stockTypeCode = stockTypeCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLotSize() {
        return lotSize;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getStockChildType() {
        return stockChildType;
    }

    public void setStockChildType(String stockChildType) {
        this.stockChildType = stockChildType;
    }

    public String getStockOwner() {
        return stockOwner;
    }

    public void setStockOwner(String stockOwner) {
        this.stockOwner = stockOwner;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public String getStrikeTime() {
        return strikeTime;
    }

    public void setStrikeTime(String strikeTime) {
        this.strikeTime = strikeTime;
    }

    public Double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getSuspension() {
        return suspension;
    }

    public void setSuspension(String suspension) {
        this.suspension = suspension;
    }

    public String getListingDate() {
        return listingDate;
    }

    public void setListingDate(String listingDate) {
        this.listingDate = listingDate;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getDelisting() {
        return delisting;
    }

    public void setDelisting(String delisting) {
        this.delisting = delisting;
    }

    public String getIndexOptionType() {
        return indexOptionType;
    }

    public void setIndexOptionType(String indexOptionType) {
        this.indexOptionType = indexOptionType;
    }

    public String getMainContract() {
        return mainContract;
    }

    public void setMainContract(String mainContract) {
        this.mainContract = mainContract;
    }

    public String getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(String lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }
}
