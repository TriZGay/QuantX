package io.futakotome.trade.dto.message;

import java.util.List;

public class AccFundsContent {
    private Double power; //最大购买力（此字段是按照 50% 的融资初始保证金率计算得到的 近似值。但事实上，每个标的的融资初始保证金率并不相同。我们建议您使用 查询最大可买可卖 接口返回的 最大可买 字段，来判断实际可买入的最大数量）
    private Double totalAssets; //资产净值
    private Double cash; //现金（仅单币种账户使用此字段，综合账户请使用 cashInfoList 获取分币种现金）
    private Double marketVal; //证券市值, 仅证券账户适用
    private Double frozenCash; //冻结资金
    private Double debtCash; //计息金额
    private Double avlWithdrawalCash; //现金可提（仅单币种账户使用此字段，综合账户请使用 cashInfoList 获取分币种现金可提）
    private Integer currency;            //币种，本结构体资金相关的货币类型，取值参见 Currency，期货和综合证券账户适用
    private Double availableFunds;     //可用资金，期货适用
    private Double unrealizedPL;      //未实现盈亏，期货适用
    private Double realizedPL;        //已实现盈亏，期货适用
    private Integer riskLevel;           //风控状态，参见 CltRiskLevel, 期货适用。建议统一使用 riskStatus 字段获取证券、期货账户的风险状态
    private Double initialMargin;      //初始保证金
    private Double maintenanceMargin;  //维持保证金

    private List<AccFundsCashInfo> cashInfoList;  //分币种的现金、现金可提和现金购买力（仅综合账户适用）
    private Double maxPowerShort; //卖空购买力（此字段是按照 60% 的融券保证金率计算得到的近似值。但事实上，每个标的的融券保证金率并不相同。我们建议您使用 查询最大可买可卖 接口返回的 可卖空 字段，来判断实际可卖空的最大数量。）
    private Double netCashPower;  //现金购买力（仅单币种账户使用此字段，综合账户请使用 cashInfoList 获取分币种现金购买力）
    private Double longMv;        //多头市值
    private Double shortMv;       //空头市值
    private Double pendingAsset;  //在途资产
    private Double maxWithdrawal;          //融资可提，仅证券账户适用
    private Integer riskStatus;              //风险状态，参见 CltRiskStatus，共分 9 个等级，LEVEL1是最安全，LEVEL9是最危险
    private Double marginCallMargin;       //	Margin Call 保证金

    private boolean isPdt;                //是否PDT账户，仅moomoo证券(美国)账户适用
    private String pdtSeq;            //剩余日内交易次数，仅被标记为 PDT 的moomoo证券(美国)账户适用
    private Double beginningDTBP;        //初始日内交易购买力，仅被标记为 PDT 的moomoo证券(美国)账户适用
    private Double remainingDTBP;        //剩余日内交易购买力，仅被标记为 PDT 的moomoo证券(美国)账户适用
    private Double dtCallAmount;        //日内交易待缴金额，仅被标记为 PDT 的moomoo证券(美国)账户适用
    private Integer dtStatus;                //日内交易限制情况，取值见 DTStatus。仅被标记为 PDT 的moomoo证券(美国)账户适用
    private Double securitiesAssets; // 证券资产净值
    private Double fundAssets; // 基金资产净值
    private Double bondAssets; // 债券资产净值

    private List<AccFundsMarketInfo> marketInfoList; //分市场资产信息

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

    public Double getUnrealizedPL() {
        return unrealizedPL;
    }

    public void setUnrealizedPL(Double unrealizedPL) {
        this.unrealizedPL = unrealizedPL;
    }

    public Double getRealizedPL() {
        return realizedPL;
    }

    public void setRealizedPL(Double realizedPL) {
        this.realizedPL = realizedPL;
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

    public List<AccFundsCashInfo> getCashInfoList() {
        return cashInfoList;
    }

    public void setCashInfoList(List<AccFundsCashInfo> cashInfoList) {
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

    public boolean isPdt() {
        return isPdt;
    }

    public void setPdt(boolean pdt) {
        isPdt = pdt;
    }

    public String getPdtSeq() {
        return pdtSeq;
    }

    public void setPdtSeq(String pdtSeq) {
        this.pdtSeq = pdtSeq;
    }

    public Double getBeginningDTBP() {
        return beginningDTBP;
    }

    public void setBeginningDTBP(Double beginningDTBP) {
        this.beginningDTBP = beginningDTBP;
    }

    public Double getRemainingDTBP() {
        return remainingDTBP;
    }

    public void setRemainingDTBP(Double remainingDTBP) {
        this.remainingDTBP = remainingDTBP;
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

    public Double getSecuritiesAssets() {
        return securitiesAssets;
    }

    public void setSecuritiesAssets(Double securitiesAssets) {
        this.securitiesAssets = securitiesAssets;
    }

    public Double getFundAssets() {
        return fundAssets;
    }

    public void setFundAssets(Double fundAssets) {
        this.fundAssets = fundAssets;
    }

    public Double getBondAssets() {
        return bondAssets;
    }

    public void setBondAssets(Double bondAssets) {
        this.bondAssets = bondAssets;
    }

    public List<AccFundsMarketInfo> getMarketInfoList() {
        return marketInfoList;
    }

    public void setMarketInfoList(List<AccFundsMarketInfo> marketInfoList) {
        this.marketInfoList = marketInfoList;
    }
}
