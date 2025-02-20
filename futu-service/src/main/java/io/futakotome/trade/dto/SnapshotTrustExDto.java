package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @TableName t_snapshot_trust_ex
 */
@TableName(value ="t_snapshot_trust_ex")
public class SnapshotTrustExDto implements Serializable {
    private Long id;

    private Integer market;

    private String code;

    private Double dividendYield;

    private Double aum;

    private Long outstandingUnits;

    private Double netAssetValue;

    private Double premium;

    private Integer assetClass;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(Double dividendYield) {
        this.dividendYield = dividendYield;
    }

    public Double getAum() {
        return aum;
    }

    public void setAum(Double aum) {
        this.aum = aum;
    }

    public Long getOutstandingUnits() {
        return outstandingUnits;
    }

    public void setOutstandingUnits(Long outstandingUnits) {
        this.outstandingUnits = outstandingUnits;
    }

    public Double getNetAssetValue() {
        return netAssetValue;
    }

    public void setNetAssetValue(Double netAssetValue) {
        this.netAssetValue = netAssetValue;
    }

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public Integer getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(Integer assetClass) {
        this.assetClass = assetClass;
    }
}