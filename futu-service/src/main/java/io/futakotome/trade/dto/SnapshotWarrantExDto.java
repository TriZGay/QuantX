package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName t_snapshot_warrant_ex
 */
@TableName(value ="t_snapshot_warrant_ex")
public class SnapshotWarrantExDto implements Serializable {
    private Long id;

    private Integer ownerMarket;

    private String ownerCode;

    private Double conversionRate;

    private Integer warrantType;

    private Double strikePrice;

    private String maturityTime;

    private String endTradeTime;

    private Double recoveryPrice;

    private Long streetVolumn;

    private Long issueVolumn;

    private Double streetRate;

    private Double delta;

    private Double impliedVolatility;

    private Double premium;

    private LocalDateTime maturityTimestamp;

    private LocalDateTime endTradeTimestamp;

    private Double leverage;

    private Double ipop;

    private Double breakEventPoint;

    private Double conversionPrice;

    private Double priceRecoveryRatio;

    private Double score;

    private Double upperStrikePrice;

    private Double lowerStrikePrice;

    private Integer inlinePriceStatus;

    private String issuerCode;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOwnerMarket() {
        return ownerMarket;
    }

    public void setOwnerMarket(Integer ownerMarket) {
        this.ownerMarket = ownerMarket;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Integer getWarrantType() {
        return warrantType;
    }

    public void setWarrantType(Integer warrantType) {
        this.warrantType = warrantType;
    }

    public Double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getMaturityTime() {
        return maturityTime;
    }

    public void setMaturityTime(String maturityTime) {
        this.maturityTime = maturityTime;
    }

    public String getEndTradeTime() {
        return endTradeTime;
    }

    public void setEndTradeTime(String endTradeTime) {
        this.endTradeTime = endTradeTime;
    }

    public Double getRecoveryPrice() {
        return recoveryPrice;
    }

    public void setRecoveryPrice(Double recoveryPrice) {
        this.recoveryPrice = recoveryPrice;
    }

    public Long getStreetVolumn() {
        return streetVolumn;
    }

    public void setStreetVolumn(Long streetVolumn) {
        this.streetVolumn = streetVolumn;
    }

    public Long getIssueVolumn() {
        return issueVolumn;
    }

    public void setIssueVolumn(Long issueVolumn) {
        this.issueVolumn = issueVolumn;
    }

    public Double getStreetRate() {
        return streetRate;
    }

    public void setStreetRate(Double streetRate) {
        this.streetRate = streetRate;
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }

    public Double getImpliedVolatility() {
        return impliedVolatility;
    }

    public void setImpliedVolatility(Double impliedVolatility) {
        this.impliedVolatility = impliedVolatility;
    }

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public LocalDateTime getMaturityTimestamp() {
        return maturityTimestamp;
    }

    public void setMaturityTimestamp(LocalDateTime maturityTimestamp) {
        this.maturityTimestamp = maturityTimestamp;
    }

    public LocalDateTime getEndTradeTimestamp() {
        return endTradeTimestamp;
    }

    public void setEndTradeTimestamp(LocalDateTime endTradeTimestamp) {
        this.endTradeTimestamp = endTradeTimestamp;
    }

    public Double getLeverage() {
        return leverage;
    }

    public void setLeverage(Double leverage) {
        this.leverage = leverage;
    }

    public Double getIpop() {
        return ipop;
    }

    public void setIpop(Double ipop) {
        this.ipop = ipop;
    }

    public Double getBreakEventPoint() {
        return breakEventPoint;
    }

    public void setBreakEventPoint(Double breakEventPoint) {
        this.breakEventPoint = breakEventPoint;
    }

    public Double getConversionPrice() {
        return conversionPrice;
    }

    public void setConversionPrice(Double conversionPrice) {
        this.conversionPrice = conversionPrice;
    }

    public Double getPriceRecoveryRatio() {
        return priceRecoveryRatio;
    }

    public void setPriceRecoveryRatio(Double priceRecoveryRatio) {
        this.priceRecoveryRatio = priceRecoveryRatio;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getUpperStrikePrice() {
        return upperStrikePrice;
    }

    public void setUpperStrikePrice(Double upperStrikePrice) {
        this.upperStrikePrice = upperStrikePrice;
    }

    public Double getLowerStrikePrice() {
        return lowerStrikePrice;
    }

    public void setLowerStrikePrice(Double lowerStrikePrice) {
        this.lowerStrikePrice = lowerStrikePrice;
    }

    public Integer getInlinePriceStatus() {
        return inlinePriceStatus;
    }

    public void setInlinePriceStatus(Integer inlinePriceStatus) {
        this.inlinePriceStatus = inlinePriceStatus;
    }

    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
    }
}