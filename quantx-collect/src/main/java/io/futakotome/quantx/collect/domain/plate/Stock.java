package io.futakotome.quantx.collect.domain.plate;

import io.futakotome.quantx.collect.utils.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_stock")
@NamedQuery(name = "Stock.findAll", query = "select s from Stock s")
@NamedNativeQuery(name = "Stock.insertOne",
        query = "insert into t_stock(name, code, lot_size, stock_type, stock_child_type, stock_owner, option_type, strike_time, strike_price, suspension, listing_date, stock_id, delisting, index_option_type, main_contract, last_trade_time, exchange_type) values (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17)")
public class Stock {
    public static final String FIND_ALL = "Stock.findAll";
    public static final String INSERT_ONE = "Stock.insertOne";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_stock_id_seq")
    @Column(name = "id")
    private Long id;
    //股票名称
    @Column(name = "name", length = 20)
    private String name;
    //股票代码
    @Column(name = "code", length = 20)
    private String code;
    //每手股数，期权表示每份合约股数 期货表示合约乘数
    @Column(name = "lot_size")
    private Integer lotSize;
    //股票类型
    @Column(name = "stock_type", length = 10)
    private String stockType;
    //窝轮子类型
    @Column(name = "stock_child_type", length = 10)
    private String stockChildType;
    //窝轮所属正股的代码，或期权标的股的代码
    @Column(name = "stock_owner", length = 20)
    private String stockOwner;
    //期权类型
    @Column(name = "option_type", length = 10)
    private String optionType;
    //期权行权日
    @Column(name = "strike_time", length = 20)
    private String strikeTime;
    //期权行权价
    @Column(name = "strike_price")
    private Double strikePrice;
    //期权是否停牌 1:true 停 0:false 未 停
    @Column(name = "suspension")
    private Integer suspension;
    //上市时间
    @Column(name = "listing_date", length = 12)
    private String listingDate;
    //股票id
    @Column(name = "stock_id")
    private Long stockId;
    //是否退市 1:true 0:false
    @Column(name = "delisting")
    private Integer delisting;
    //指数期权类型
    @Column(name = "index_option_type", length = 20)
    private String indexOptionType;
    //是否主连合约 1:true 0:false
    @Column(name = "main_contract")
    private Integer mainContract;
    // 最后交易时间
    @Column(name = "last_trade_time", length = 20)
    private String lastTradeTime;
    //所属交易所
    @Column(name = "exchange_type", length = 20)
    private String exchangeType;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    public void updateConditional(Stock stock) {
        if (StringUtils.isNotEmpty(stock.getName())) {
            this.setName(stock.getName());
        }
        if (StringUtils.isNotEmpty(stock.getCode())) {
            this.setCode(stock.getCode());
        }
        if (StringUtils.isNotEmpty(stock.getLotSize())) {
            this.setLotSize(stock.getLotSize());
        }
        if (StringUtils.isNotEmpty(stock.getStockType())) {
            this.setStockType(stock.getStockType());
        }
        if (StringUtils.isNotEmpty(stock.getStockChildType())) {
            this.setStockChildType(stock.getStockChildType());
        }
        if (StringUtils.isNotEmpty(stock.getStockOwner())) {
            this.setStockOwner(stock.getStockOwner());
        }
        if (StringUtils.isNotEmpty(stock.getOptionType())) {
            this.setOptionType(stock.getOptionType());
        }
        if (StringUtils.isNotEmpty(stock.getStrikeTime())) {
            this.setStrikeTime(stock.getStrikeTime());
        }
        if (StringUtils.isNotEmpty(stock.getStrikePrice())) {
            this.setStrikePrice(stock.getStrikePrice());
        }
        if (StringUtils.isNotEmpty(stock.getSuspension())) {
            this.setSuspension(stock.getSuspension());
        }
        if (StringUtils.isNotEmpty(stock.getListingDate())) {
            this.setListingDate(stock.getListingDate());
        }
        if (StringUtils.isNotEmpty(stock.getStockId())) {
            this.setStockId(stock.getStockId());
        }
        if (StringUtils.isNotEmpty(stock.isDelisting())) {
            this.setDelisting(stock.isDelisting());
        }
        if (StringUtils.isNotEmpty(stock.getIndexOptionType())) {
            this.setIndexOptionType(stock.getIndexOptionType());
        }
        if (StringUtils.isNotEmpty(stock.isMainContract())) {
            this.setMainContract(stock.isMainContract());
        }
        if (StringUtils.isNotEmpty(stock.getLastTradeTime())) {
            this.setLastTradeTime(stock.getLastTradeTime());
        }
        if (StringUtils.isNotEmpty(stock.getExchangeType())) {
            this.setExchangeType(stock.getExchangeType());
        }
        this.setModifyDate(LocalDateTime.now());
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

    public Integer getSuspension() {
        return suspension;
    }

    public void setSuspension(Integer suspension) {
        this.suspension = suspension;
    }

    public String getListingDate() {
        return listingDate;
    }

    public void setListingDate(String listingDate) {
        this.listingDate = listingDate;
    }

    public Integer isDelisting() {
        return delisting;
    }

    public void setDelisting(Integer delisting) {
        this.delisting = delisting;
    }

    public String getIndexOptionType() {
        return indexOptionType;
    }

    public void setIndexOptionType(String indexOptionType) {
        this.indexOptionType = indexOptionType;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Integer isMainContract() {
        return mainContract;
    }

    public void setMainContract(Integer mainContract) {
        this.mainContract = mainContract;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", lotSize=" + lotSize +
                ", stockType='" + stockType + '\'' +
                ", stockChildType='" + stockChildType + '\'' +
                ", stockOwner='" + stockOwner + '\'' +
                ", optionType='" + optionType + '\'' +
                ", strikeTime='" + strikeTime + '\'' +
                ", strikePrice=" + strikePrice +
                ", suspension=" + suspension +
                ", listingDate='" + listingDate + '\'' +
                ", stockId=" + stockId +
                ", delisting=" + delisting +
                ", indexOptionType='" + indexOptionType + '\'' +
                ", mainContract=" + mainContract +
                ", lastTradeTime='" + lastTradeTime + '\'' +
                ", exchangeType='" + exchangeType + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }
}
