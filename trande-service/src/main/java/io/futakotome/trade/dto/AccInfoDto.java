package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @TableName t_acc_info
 */
@TableName(value = "t_acc_info")
public class AccInfoDto implements Serializable {
    @TableId
    private Long id;

    private String accId;

    private Double power;

    private Double totalAssets;

    private Double cash;

    private Double marketVal;

    private Double frozenCash;

    private Double debtCash;

    private Double avlWithdrawalCash;

    private Integer currency;

    private Double availableFunds;

    private Double unrealizedPl;

    private Double realizedPl;

    private Integer riskLevel;

    private Double initialMargin;

    private Double maintenanceMargin;

    private String cashInfoList;

    private Double maxPowerShort;

    private Double netCashPower;

    private Double longMv;

    private Double shortMv;

    private Double pendingAsset;

    private Double maxWithdrawal;

    private Integer riskStatus;

    private Double marginCallMargin;

    private Integer isPdt;

    private String pdtSeq;

    private Double beginningDtbp;

    private Double remainingDtbp;

    private Double dtCallAmount;

    private Integer dtStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getMarketVal() {
        return marketVal;
    }

    public void setMarketVal(Double marketVal) {
        this.marketVal = marketVal;
    }

    public Double getFrozenCash() {
        return frozenCash;
    }

    public void setFrozenCash(Double frozenCash) {
        this.frozenCash = frozenCash;
    }

    public Double getDebtCash() {
        return debtCash;
    }

    public void setDebtCash(Double debtCash) {
        this.debtCash = debtCash;
    }

    public Double getAvlWithdrawalCash() {
        return avlWithdrawalCash;
    }

    public void setAvlWithdrawalCash(Double avlWithdrawalCash) {
        this.avlWithdrawalCash = avlWithdrawalCash;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Double getAvailableFunds() {
        return availableFunds;
    }

    public void setAvailableFunds(Double availableFunds) {
        this.availableFunds = availableFunds;
    }

    public Double getUnrealizedPl() {
        return unrealizedPl;
    }

    public void setUnrealizedPl(Double unrealizedPl) {
        this.unrealizedPl = unrealizedPl;
    }

    public Double getRealizedPl() {
        return realizedPl;
    }

    public void setRealizedPl(Double realizedPl) {
        this.realizedPl = realizedPl;
    }

    public Integer getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Double getInitialMargin() {
        return initialMargin;
    }

    public void setInitialMargin(Double initialMargin) {
        this.initialMargin = initialMargin;
    }

    public Double getMaintenanceMargin() {
        return maintenanceMargin;
    }

    public void setMaintenanceMargin(Double maintenanceMargin) {
        this.maintenanceMargin = maintenanceMargin;
    }

    public String getCashInfoList() {
        return cashInfoList;
    }

    public void setCashInfoList(String cashInfoList) {
        this.cashInfoList = cashInfoList;
    }

    public Double getMaxPowerShort() {
        return maxPowerShort;
    }

    public void setMaxPowerShort(Double maxPowerShort) {
        this.maxPowerShort = maxPowerShort;
    }

    public Double getNetCashPower() {
        return netCashPower;
    }

    public void setNetCashPower(Double netCashPower) {
        this.netCashPower = netCashPower;
    }

    public Double getLongMv() {
        return longMv;
    }

    public void setLongMv(Double longMv) {
        this.longMv = longMv;
    }

    public Double getShortMv() {
        return shortMv;
    }

    public void setShortMv(Double shortMv) {
        this.shortMv = shortMv;
    }

    public Double getPendingAsset() {
        return pendingAsset;
    }

    public void setPendingAsset(Double pendingAsset) {
        this.pendingAsset = pendingAsset;
    }

    public Double getMaxWithdrawal() {
        return maxWithdrawal;
    }

    public void setMaxWithdrawal(Double maxWithdrawal) {
        this.maxWithdrawal = maxWithdrawal;
    }

    public Integer getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(Integer riskStatus) {
        this.riskStatus = riskStatus;
    }

    public Double getMarginCallMargin() {
        return marginCallMargin;
    }

    public void setMarginCallMargin(Double marginCallMargin) {
        this.marginCallMargin = marginCallMargin;
    }

    public Integer getIsPdt() {
        return isPdt;
    }

    public void setIsPdt(Integer isPdt) {
        this.isPdt = isPdt;
    }

    public String getPdtSeq() {
        return pdtSeq;
    }

    public void setPdtSeq(String pdtSeq) {
        this.pdtSeq = pdtSeq;
    }

    public Double getBeginningDtbp() {
        return beginningDtbp;
    }

    public void setBeginningDtbp(Double beginningDtbp) {
        this.beginningDtbp = beginningDtbp;
    }

    public Double getRemainingDtbp() {
        return remainingDtbp;
    }

    public void setRemainingDtbp(Double remainingDtbp) {
        this.remainingDtbp = remainingDtbp;
    }

    public Double getDtCallAmount() {
        return dtCallAmount;
    }

    public void setDtCallAmount(Double dtCallAmount) {
        this.dtCallAmount = dtCallAmount;
    }

    public Integer getDtStatus() {
        return dtStatus;
    }

    public void setDtStatus(Integer dtStatus) {
        this.dtStatus = dtStatus;
    }
}