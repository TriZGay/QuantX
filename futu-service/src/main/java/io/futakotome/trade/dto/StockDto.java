package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@TableName(value = "t_stock")
public class StockDto implements Serializable {
    @TableId
    private Long id;

    private String name;

    private Integer market;

    private String code;

    private Integer lotSize;

    private Integer stockType;

    private Integer stockChildType;

    private String stockOwner;

    private Integer optionType;

    private String strikeTime;

    private Double strikePrice;

    private String optionMarket;

    private Boolean suspension;

    private LocalDate listingDate;

    private String stockId;

    private Integer delisting;

    private Integer indexOptionType;

    private Integer mainContract;

    private LocalDate lastTradeTime;

    private Integer exchangeType;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public Integer getStockChildType() {
        return stockChildType;
    }

    public void setStockChildType(Integer stockChildType) {
        this.stockChildType = stockChildType;
    }

    public String getStockOwner() {
        return stockOwner;
    }

    public void setStockOwner(String stockOwner) {
        this.stockOwner = stockOwner;
    }

    public Integer getOptionType() {
        return optionType;
    }

    public void setOptionType(Integer optionType) {
        this.optionType = optionType;
    }

    public String getOptionMarket() {
        return optionMarket;
    }

    public void setOptionMarket(String optionMarket) {
        this.optionMarket = optionMarket;
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

    public Boolean getSuspension() {
        return suspension;
    }

    public void setSuspension(Boolean suspension) {
        this.suspension = suspension;
    }

    public LocalDate getListingDate() {
        return listingDate;
    }

    public void setListingDate(LocalDate listingDate) {
        this.listingDate = listingDate;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Integer getDelisting() {
        return delisting;
    }

    public void setDelisting(Integer delisting) {
        this.delisting = delisting;
    }

    public Integer getIndexOptionType() {
        return indexOptionType;
    }

    public void setIndexOptionType(Integer indexOptionType) {
        this.indexOptionType = indexOptionType;
    }

    public Integer getMainContract() {
        return mainContract;
    }

    public void setMainContract(Integer mainContract) {
        this.mainContract = mainContract;
    }

    public LocalDate getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(LocalDate lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public Integer getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(Integer exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDto stockDto = (StockDto) o;
        return code.equals(stockDto.code) && market.equals(stockDto.market);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, market);
    }

}