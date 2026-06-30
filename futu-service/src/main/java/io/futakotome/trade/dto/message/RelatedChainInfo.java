package io.futakotome.trade.dto.message;

public class RelatedChainInfo {
    private Long chainId;             // 产业链ID
    private Integer chainType;           // 类型
    private String name;               // 名称
    private Double marketCap;          // 市值
    private Long stocksNum;           // 成分股数量

    public Long getChainId() {
        return chainId;
    }

    public void setChainId(Long chainId) {
        this.chainId = chainId;
    }

    public Integer getChainType() {
        return chainType;
    }

    public void setChainType(Integer chainType) {
        this.chainType = chainType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Long getStocksNum() {
        return stocksNum;
    }

    public void setStocksNum(Long stocksNum) {
        this.stocksNum = stocksNum;
    }
}
