package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * @TableName t_snapshot_equity_ex
 */
@TableName(value ="t_snapshot_equity_ex")
public class SnapshotEquityExDto implements Serializable {
    private Long id;

    private Integer market;

    private String code;

    private Long issuedShares;

    private Double issuedMarketVal;

    private Double netAsset;

    private Double netProfit;

    private Double earningsPerShare;

    private Long outstandingShares;

    private Double outstandingMarketVal;

    private Double netAssetPerShare;

    private Double eyRate;

    private Double peRate;

    private Double pbRate;

    private Double peTtmRate;

    private Double dividendTtm;

    private Double dividendRatioTtm;

    private Double dividendLfy;

    private Double dividendLfyRatio;

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

    public Long getIssuedShares() {
        return issuedShares;
    }

    public void setIssuedShares(Long issuedShares) {
        this.issuedShares = issuedShares;
    }

    public Double getIssuedMarketVal() {
        return issuedMarketVal;
    }

    public void setIssuedMarketVal(Double issuedMarketVal) {
        this.issuedMarketVal = issuedMarketVal;
    }

    public Double getNetAsset() {
        return netAsset;
    }

    public void setNetAsset(Double netAsset) {
        this.netAsset = netAsset;
    }

    public Double getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(Double netProfit) {
        this.netProfit = netProfit;
    }

    public Double getEarningsPerShare() {
        return earningsPerShare;
    }

    public void setEarningsPerShare(Double earningsPerShare) {
        this.earningsPerShare = earningsPerShare;
    }

    public Long getOutstandingShares() {
        return outstandingShares;
    }

    public void setOutstandingShares(Long outstandingShares) {
        this.outstandingShares = outstandingShares;
    }

    public Double getOutstandingMarketVal() {
        return outstandingMarketVal;
    }

    public void setOutstandingMarketVal(Double outstandingMarketVal) {
        this.outstandingMarketVal = outstandingMarketVal;
    }

    public Double getNetAssetPerShare() {
        return netAssetPerShare;
    }

    public void setNetAssetPerShare(Double netAssetPerShare) {
        this.netAssetPerShare = netAssetPerShare;
    }

    public Double getEyRate() {
        return eyRate;
    }

    public void setEyRate(Double eyRate) {
        this.eyRate = eyRate;
    }

    public Double getPeRate() {
        return peRate;
    }

    public void setPeRate(Double peRate) {
        this.peRate = peRate;
    }

    public Double getPbRate() {
        return pbRate;
    }

    public void setPbRate(Double pbRate) {
        this.pbRate = pbRate;
    }

    public Double getPeTtmRate() {
        return peTtmRate;
    }

    public void setPeTtmRate(Double peTtmRate) {
        this.peTtmRate = peTtmRate;
    }

    public Double getDividendTtm() {
        return dividendTtm;
    }

    public void setDividendTtm(Double dividendTtm) {
        this.dividendTtm = dividendTtm;
    }

    public Double getDividendRatioTtm() {
        return dividendRatioTtm;
    }

    public void setDividendRatioTtm(Double dividendRatioTtm) {
        this.dividendRatioTtm = dividendRatioTtm;
    }

    public Double getDividendLfy() {
        return dividendLfy;
    }

    public void setDividendLfy(Double dividendLfy) {
        this.dividendLfy = dividendLfy;
    }

    public Double getDividendLfyRatio() {
        return dividendLfyRatio;
    }

    public void setDividendLfyRatio(Double dividendLfyRatio) {
        this.dividendLfyRatio = dividendLfyRatio;
    }
}