package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * @TableName t_snapshot_option_ex
 */
@TableName(value ="t_snapshot_option_ex")
public class SnapshotOptionExDto implements Serializable {
    private Long id;

    private Integer ownerMarket;

    private String ownerCode;

    private Integer optionType;

    private String strikeTime;

    private Double strikePrice;

    private Integer contractSize;

    private Double contractSizeFloat;

    private Integer openInterest;

    private Double impliedVolatility;

    private Double premium;

    private Double delta;

    private Double gamma;

    private Double vega;

    private Double theta;

    private Double rho;

    private Integer indexOptionType;

    private Integer netOpenInterest;

    private Integer expiryDateDistance;

    private Double contractNominalValue;

    private Double ownerLotMultiplier;

    private Integer optionAreaType;

    private Double contractMultiplier;

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

    public Integer getOptionType() {
        return optionType;
    }

    public void setOptionType(Integer optionType) {
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

    public Integer getContractSize() {
        return contractSize;
    }

    public void setContractSize(Integer contractSize) {
        this.contractSize = contractSize;
    }

    public Double getContractSizeFloat() {
        return contractSizeFloat;
    }

    public void setContractSizeFloat(Double contractSizeFloat) {
        this.contractSizeFloat = contractSizeFloat;
    }

    public Integer getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(Integer openInterest) {
        this.openInterest = openInterest;
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

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }

    public Double getGamma() {
        return gamma;
    }

    public void setGamma(Double gamma) {
        this.gamma = gamma;
    }

    public Double getVega() {
        return vega;
    }

    public void setVega(Double vega) {
        this.vega = vega;
    }

    public Double getTheta() {
        return theta;
    }

    public void setTheta(Double theta) {
        this.theta = theta;
    }

    public Double getRho() {
        return rho;
    }

    public void setRho(Double rho) {
        this.rho = rho;
    }

    public Integer getIndexOptionType() {
        return indexOptionType;
    }

    public void setIndexOptionType(Integer indexOptionType) {
        this.indexOptionType = indexOptionType;
    }

    public Integer getNetOpenInterest() {
        return netOpenInterest;
    }

    public void setNetOpenInterest(Integer netOpenInterest) {
        this.netOpenInterest = netOpenInterest;
    }

    public Integer getExpiryDateDistance() {
        return expiryDateDistance;
    }

    public void setExpiryDateDistance(Integer expiryDateDistance) {
        this.expiryDateDistance = expiryDateDistance;
    }

    public Double getContractNominalValue() {
        return contractNominalValue;
    }

    public void setContractNominalValue(Double contractNominalValue) {
        this.contractNominalValue = contractNominalValue;
    }

    public Double getOwnerLotMultiplier() {
        return ownerLotMultiplier;
    }

    public void setOwnerLotMultiplier(Double ownerLotMultiplier) {
        this.ownerLotMultiplier = ownerLotMultiplier;
    }

    public Integer getOptionAreaType() {
        return optionAreaType;
    }

    public void setOptionAreaType(Integer optionAreaType) {
        this.optionAreaType = optionAreaType;
    }

    public Double getContractMultiplier() {
        return contractMultiplier;
    }

    public void setContractMultiplier(Double contractMultiplier) {
        this.contractMultiplier = contractMultiplier;
    }
}