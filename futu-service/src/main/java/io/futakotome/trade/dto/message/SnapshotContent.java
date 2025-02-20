package io.futakotome.trade.dto.message;

public class SnapshotContent {
    private SnapshotBasicData basic; //快照基本数据
    private EquitySnapshotExData equityExData; //正股快照额外数据
    private WarrantSnapshotExData warrantExData; //窝轮快照额外数据
    private OptionSnapshotExData optionExData; //期权快照额外数据
    private IndexSnapshotExData indexExData; //指数快照额外数据
    private PlateSnapshotExData plateExData; //板块快照额外数据
    private FutureSnapshotExData futureExData; //期货类型额外数据
    private TrustSnapshotExData trustExData; //基金类型额外数据

    public SnapshotBasicData getBasic() {
        return basic;
    }

    public void setBasic(SnapshotBasicData basic) {
        this.basic = basic;
    }

    public EquitySnapshotExData getEquityExData() {
        return equityExData;
    }

    public void setEquityExData(EquitySnapshotExData equityExData) {
        this.equityExData = equityExData;
    }

    public WarrantSnapshotExData getWarrantExData() {
        return warrantExData;
    }

    public void setWarrantExData(WarrantSnapshotExData warrantExData) {
        this.warrantExData = warrantExData;
    }

    public OptionSnapshotExData getOptionExData() {
        return optionExData;
    }

    public void setOptionExData(OptionSnapshotExData optionExData) {
        this.optionExData = optionExData;
    }

    public IndexSnapshotExData getIndexExData() {
        return indexExData;
    }

    public void setIndexExData(IndexSnapshotExData indexExData) {
        this.indexExData = indexExData;
    }

    public PlateSnapshotExData getPlateExData() {
        return plateExData;
    }

    public void setPlateExData(PlateSnapshotExData plateExData) {
        this.plateExData = plateExData;
    }

    public FutureSnapshotExData getFutureExData() {
        return futureExData;
    }

    public void setFutureExData(FutureSnapshotExData futureExData) {
        this.futureExData = futureExData;
    }

    public TrustSnapshotExData getTrustExData() {
        return trustExData;
    }

    public void setTrustExData(TrustSnapshotExData trustExData) {
        this.trustExData = trustExData;
    }

    public static class SnapshotBasicData {
        private CommonSecurity security;
        private String name; //股票名称
        private Integer type; //证券类型
        private Boolean isSuspend; //是否停牌
        private String listTime; //上市时间字符串（格式：yyyy-MM-dd）
        private Integer lotSize; //每手数量
        private Double priceSpread; //价差
        private String updateTime; //更新时间字符串（格式：yyyy-MM-dd HH:mm:ss）
        private Double highPrice; //最高价
        private Double openPrice; //开盘价
        private Double lowPrice; //最低价
        private Double lastClosePrice; //昨收价
        private Double curPrice; //最新价
        private Long volume; //成交量
        private Double turnover; //成交额
        private Double turnoverRate; //换手率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double listTimestamp; //上市时间戳
        private Double updateTimestamp; //更新时间戳
        private Double askPrice;//卖价
        private Double bidPrice;//买价
        private Long askVol;//卖量
        private Long bidVol;//买量
        private Double amplitude; // 振幅（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double avgPrice; // 平均价
        private Double bidAskRatio; // 委比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double volumeRatio;  // 量比
        private Double highest52WeeksPrice;  // 52周最高价
        private Double lowest52WeeksPrice;  // 52周最低价
        private Double highestHistoryPrice;  // 历史最高价
        private Double lowestHistoryPrice;  // 历史最低价
        private CommonPreAfterMarketData preMarket; // 盘前数据
        private CommonPreAfterMarketData afterMarket; // 盘后数据
        private Integer secStatus; // 股票状态
        private Double closePrice5Minute; //5分钟收盘价

        public CommonSecurity getSecurity() {
            return security;
        }

        public void setSecurity(CommonSecurity security) {
            this.security = security;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Boolean getSuspend() {
            return isSuspend;
        }

        public void setSuspend(Boolean suspend) {
            isSuspend = suspend;
        }

        public String getListTime() {
            return listTime;
        }

        public void setListTime(String listTime) {
            this.listTime = listTime;
        }

        public Integer getLotSize() {
            return lotSize;
        }

        public void setLotSize(Integer lotSize) {
            this.lotSize = lotSize;
        }

        public Double getPriceSpread() {
            return priceSpread;
        }

        public void setPriceSpread(Double priceSpread) {
            this.priceSpread = priceSpread;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Double getHighPrice() {
            return highPrice;
        }

        public void setHighPrice(Double highPrice) {
            this.highPrice = highPrice;
        }

        public Double getOpenPrice() {
            return openPrice;
        }

        public void setOpenPrice(Double openPrice) {
            this.openPrice = openPrice;
        }

        public Double getLowPrice() {
            return lowPrice;
        }

        public void setLowPrice(Double lowPrice) {
            this.lowPrice = lowPrice;
        }

        public Double getLastClosePrice() {
            return lastClosePrice;
        }

        public void setLastClosePrice(Double lastClosePrice) {
            this.lastClosePrice = lastClosePrice;
        }

        public Double getCurPrice() {
            return curPrice;
        }

        public void setCurPrice(Double curPrice) {
            this.curPrice = curPrice;
        }

        public Long getVolume() {
            return volume;
        }

        public void setVolume(Long volume) {
            this.volume = volume;
        }

        public Double getTurnover() {
            return turnover;
        }

        public void setTurnover(Double turnover) {
            this.turnover = turnover;
        }

        public Double getTurnoverRate() {
            return turnoverRate;
        }

        public void setTurnoverRate(Double turnoverRate) {
            this.turnoverRate = turnoverRate;
        }

        public Double getListTimestamp() {
            return listTimestamp;
        }

        public void setListTimestamp(Double listTimestamp) {
            this.listTimestamp = listTimestamp;
        }

        public Double getUpdateTimestamp() {
            return updateTimestamp;
        }

        public void setUpdateTimestamp(Double updateTimestamp) {
            this.updateTimestamp = updateTimestamp;
        }

        public Double getAskPrice() {
            return askPrice;
        }

        public void setAskPrice(Double askPrice) {
            this.askPrice = askPrice;
        }

        public Double getBidPrice() {
            return bidPrice;
        }

        public void setBidPrice(Double bidPrice) {
            this.bidPrice = bidPrice;
        }

        public Long getAskVol() {
            return askVol;
        }

        public void setAskVol(Long askVol) {
            this.askVol = askVol;
        }

        public Long getBidVol() {
            return bidVol;
        }

        public void setBidVol(Long bidVol) {
            this.bidVol = bidVol;
        }

        public Double getAmplitude() {
            return amplitude;
        }

        public void setAmplitude(Double amplitude) {
            this.amplitude = amplitude;
        }

        public Double getAvgPrice() {
            return avgPrice;
        }

        public void setAvgPrice(Double avgPrice) {
            this.avgPrice = avgPrice;
        }

        public Double getBidAskRatio() {
            return bidAskRatio;
        }

        public void setBidAskRatio(Double bidAskRatio) {
            this.bidAskRatio = bidAskRatio;
        }

        public Double getVolumeRatio() {
            return volumeRatio;
        }

        public void setVolumeRatio(Double volumeRatio) {
            this.volumeRatio = volumeRatio;
        }

        public Double getHighest52WeeksPrice() {
            return highest52WeeksPrice;
        }

        public void setHighest52WeeksPrice(Double highest52WeeksPrice) {
            this.highest52WeeksPrice = highest52WeeksPrice;
        }

        public Double getLowest52WeeksPrice() {
            return lowest52WeeksPrice;
        }

        public void setLowest52WeeksPrice(Double lowest52WeeksPrice) {
            this.lowest52WeeksPrice = lowest52WeeksPrice;
        }

        public Double getHighestHistoryPrice() {
            return highestHistoryPrice;
        }

        public void setHighestHistoryPrice(Double highestHistoryPrice) {
            this.highestHistoryPrice = highestHistoryPrice;
        }

        public Double getLowestHistoryPrice() {
            return lowestHistoryPrice;
        }

        public void setLowestHistoryPrice(Double lowestHistoryPrice) {
            this.lowestHistoryPrice = lowestHistoryPrice;
        }

        public CommonPreAfterMarketData getPreMarket() {
            return preMarket;
        }

        public void setPreMarket(CommonPreAfterMarketData preMarket) {
            this.preMarket = preMarket;
        }

        public CommonPreAfterMarketData getAfterMarket() {
            return afterMarket;
        }

        public void setAfterMarket(CommonPreAfterMarketData afterMarket) {
            this.afterMarket = afterMarket;
        }

        public Integer getSecStatus() {
            return secStatus;
        }

        public void setSecStatus(Integer secStatus) {
            this.secStatus = secStatus;
        }

        public Double getClosePrice5Minute() {
            return closePrice5Minute;
        }

        public void setClosePrice5Minute(Double closePrice5Minute) {
            this.closePrice5Minute = closePrice5Minute;
        }
    }

    public static class EquitySnapshotExData {
        private Long issuedShares; // 总股本
        private Double issuedMarketVal; // 总市值 =总股本*当前价格
        private Double netAsset; // 资产净值
        private Double netProfit; // 盈利（亏损）
        private Double earningsPershare; // 每股盈利
        private Long outstandingShares; // 流通股本
        private Double outstandingMarketVal; // 流通市值 =流通股本*当前价格
        private Double netAssetPershare; // 每股净资产
        private Double eyRate; // 收益率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double peRate; // 市盈率
        private Double pbRate; // 市净率
        private Double peTTMRate; // 市盈率 TTM
        private Double dividendTTM; // 股息 TTM，派息
        private Double dividendRatioTTM; // 股息率 TTM（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double dividendLFY; // 股息 LFY，上一年度派息
        private Double dividendLFYRatio; // 股息率 LFY（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）

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

        public Double getEarningsPershare() {
            return earningsPershare;
        }

        public void setEarningsPershare(Double earningsPershare) {
            this.earningsPershare = earningsPershare;
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

        public Double getNetAssetPershare() {
            return netAssetPershare;
        }

        public void setNetAssetPershare(Double netAssetPershare) {
            this.netAssetPershare = netAssetPershare;
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

        public Double getPeTTMRate() {
            return peTTMRate;
        }

        public void setPeTTMRate(Double peTTMRate) {
            this.peTTMRate = peTTMRate;
        }

        public Double getDividendTTM() {
            return dividendTTM;
        }

        public void setDividendTTM(Double dividendTTM) {
            this.dividendTTM = dividendTTM;
        }

        public Double getDividendRatioTTM() {
            return dividendRatioTTM;
        }

        public void setDividendRatioTTM(Double dividendRatioTTM) {
            this.dividendRatioTTM = dividendRatioTTM;
        }

        public Double getDividendLFY() {
            return dividendLFY;
        }

        public void setDividendLFY(Double dividendLFY) {
            this.dividendLFY = dividendLFY;
        }

        public Double getDividendLFYRatio() {
            return dividendLFYRatio;
        }

        public void setDividendLFYRatio(Double dividendLFYRatio) {
            this.dividendLFYRatio = dividendLFYRatio;
        }
    }

    public static class WarrantSnapshotExData {
        private Double conversionRate; //换股比率
        private Integer warrantType; //Qot_Common.WarrantType，窝轮类型
        private Double strikePrice; //行使价
        private String maturityTime; //到期日时间字符串
        private String endTradeTime; //最后交易日时间字符串
        private CommonSecurity owner; //所属正股
        private Double recoveryPrice; //收回价，仅牛熊证支持该字段
        private Long streetVolumn; //街货量
        private Long issueVolumn; //发行量
        private Double streetRate; //街货占比（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double delta; //对冲值，仅认购认沽支持该字段
        private Double impliedVolatility; //引申波幅，仅认购认沽支持该字段
        private Double premium; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double maturityTimestamp; //到期日时间戳
        private Double endTradeTimestamp; //最后交易日时间戳
        private Double leverage;  // 杠杆比率（倍）
        private Double ipop; // 价内/价外（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double breakEvenPoint; // 打和点
        private Double conversionPrice;  // 换股价
        private Double priceRecoveryRatio; // 正股距收回价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double score; // 综合评分
        private Double upperStrikePrice; //上限价，仅界内证支持该字段
        private Double lowerStrikePrice; //下限价，仅界内证支持该字段
        private Integer inLinePriceStatus; //Qot_Common.PriceType，界内界外，仅界内证支持该字段
        private String issuerCode; //发行人代码

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

        public CommonSecurity getOwner() {
            return owner;
        }

        public void setOwner(CommonSecurity owner) {
            this.owner = owner;
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

        public Double getMaturityTimestamp() {
            return maturityTimestamp;
        }

        public void setMaturityTimestamp(Double maturityTimestamp) {
            this.maturityTimestamp = maturityTimestamp;
        }

        public Double getEndTradeTimestamp() {
            return endTradeTimestamp;
        }

        public void setEndTradeTimestamp(Double endTradeTimestamp) {
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

        public Double getBreakEvenPoint() {
            return breakEvenPoint;
        }

        public void setBreakEvenPoint(Double breakEvenPoint) {
            this.breakEvenPoint = breakEvenPoint;
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

        public Integer getInLinePriceStatus() {
            return inLinePriceStatus;
        }

        public void setInLinePriceStatus(Integer inLinePriceStatus) {
            this.inLinePriceStatus = inLinePriceStatus;
        }

        public String getIssuerCode() {
            return issuerCode;
        }

        public void setIssuerCode(String issuerCode) {
            this.issuerCode = issuerCode;
        }
    }

    public static class OptionSnapshotExData {
        private Integer type; //Qot_Common.OptionType，期权类型（按方向）
        private CommonSecurity owner; //标的股
        private String strikeTime; //行权日时间字符串（格式：yyyy-MM-dd）
        private Double strikePrice; //行权价
        private Integer contractSize; //每份合约数(整型数据)
        private Double contractSizeFloat; //每份合约数（浮点型数据）
        private Integer openInterest; //未平仓合约数
        private Double impliedVolatility; //隐含波动率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double premium; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double delta; //希腊值 Delta
        private Double gamma; //希腊值 Gamma
        private Double vega; //希腊值 Vega
        private Double theta; //希腊值 Theta
        private Double rho; //希腊值 Rho
        private Double strikeTimestamp; //行权日时间戳
        private Integer indexOptionType; //Qot_Common.IndexOptionType，指数期权类型
        private Integer netOpenInterest; //净未平仓合约数，仅港股期权适用
        private Integer expiryDateDistance; //距离到期日天数，负数表示已过期
        private Double contractNominalValue; //合约名义金额，仅港股期权适用
        private Double ownerLotMultiplier; //相等正股手数，指数期权无该字段，仅港股期权适用
        private Integer optionAreaType; //Qot_Common.OptionAreaType，期权类型（按行权时间）
        private Double contractMultiplier; //合约乘数

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public CommonSecurity getOwner() {
            return owner;
        }

        public void setOwner(CommonSecurity owner) {
            this.owner = owner;
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

        public Double getStrikeTimestamp() {
            return strikeTimestamp;
        }

        public void setStrikeTimestamp(Double strikeTimestamp) {
            this.strikeTimestamp = strikeTimestamp;
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

    public static class IndexSnapshotExData {
        private Integer raiseCount;  // 上涨支数
        private Integer fallCount;  // 下跌支数
        private Integer equalCount;  // 平盘支数

        public Integer getRaiseCount() {
            return raiseCount;
        }

        public void setRaiseCount(Integer raiseCount) {
            this.raiseCount = raiseCount;
        }

        public Integer getFallCount() {
            return fallCount;
        }

        public void setFallCount(Integer fallCount) {
            this.fallCount = fallCount;
        }

        public Integer getEqualCount() {
            return equalCount;
        }

        public void setEqualCount(Integer equalCount) {
            this.equalCount = equalCount;
        }
    }

    public static class PlateSnapshotExData {
        private Integer raiseCount;  // 上涨支数
        private Integer fallCount;  // 下跌支数
        private Integer equalCount;  // 平盘支数

        public Integer getRaiseCount() {
            return raiseCount;
        }

        public void setRaiseCount(Integer raiseCount) {
            this.raiseCount = raiseCount;
        }

        public Integer getFallCount() {
            return fallCount;
        }

        public void setFallCount(Integer fallCount) {
            this.fallCount = fallCount;
        }

        public Integer getEqualCount() {
            return equalCount;
        }

        public void setEqualCount(Integer equalCount) {
            this.equalCount = equalCount;
        }
    }

    public static class FutureSnapshotExData {
        private Double lastSettlePrice; //昨结
        private Integer position; //持仓量
        private Integer positionChange; //日增仓
        private String lastTradeTime; //最后交易日，只有非主连期货合约才有该字段
        private Double lastTradeTimestamp; //最后交易日时间戳，只有非主连期货合约才有该字段
        private Boolean isMainContract; //是否主连合约

        public Double getLastSettlePrice() {
            return lastSettlePrice;
        }

        public void setLastSettlePrice(Double lastSettlePrice) {
            this.lastSettlePrice = lastSettlePrice;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }

        public Integer getPositionChange() {
            return positionChange;
        }

        public void setPositionChange(Integer positionChange) {
            this.positionChange = positionChange;
        }

        public String getLastTradeTime() {
            return lastTradeTime;
        }

        public void setLastTradeTime(String lastTradeTime) {
            this.lastTradeTime = lastTradeTime;
        }

        public Double getLastTradeTimestamp() {
            return lastTradeTimestamp;
        }

        public void setLastTradeTimestamp(Double lastTradeTimestamp) {
            this.lastTradeTimestamp = lastTradeTimestamp;
        }

        public Boolean getMainContract() {
            return isMainContract;
        }

        public void setMainContract(Boolean mainContract) {
            isMainContract = mainContract;
        }
    }

    public static class TrustSnapshotExData {
        private Double dividendYield; //股息率（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Double aum; //资产规模
        private Long outstandingUnits; //总发行量
        private Double netAssetValue; //单位净值
        private Double premium; //溢价（该字段为百分比字段，默认不展示 %，如 20 实际对应 20%）
        private Integer assetClass; //资产类别

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
}
