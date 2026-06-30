package io.futakotome.trade.dto.message;

import java.util.List;

public class IndustrialChainInfo {
    private Long chainId;                             // 产业链ID
    private Integer chainType;                           // IndustrialChainType
    private String name;                               // 名称
    private String detail;                             // 详情描述
    private Double marketCap;                          // 市值
    private Long stocksNum;                           // 成分股数量
    private List<CommonSecurity> relationSecurityList;  // 相关股票

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public List<CommonSecurity> getRelationSecurityList() {
        return relationSecurityList;
    }

    public void setRelationSecurityList(List<CommonSecurity> relationSecurityList) {
        this.relationSecurityList = relationSecurityList;
    }
}
