package io.futakotome.trade.dto.message;

public class PositionMessageContent {
    private String positionID;     //持仓 ID，一条持仓的唯一标识
    private Integer positionSide;    //持仓方向，参见 PositionSide 的枚举定义
    private String positionSideStr;
    private String code;           //代码
    private String name;           //名称
    private Double qty;            //持有数量，2位精度，期权单位是"张"，下同
    private Double canSellQty;     //可用数量，是指持有的可平仓的数量。可用数量=持有数量-冻结数量。期权和期货的单位是“张”。
    private Double price;          //市价，3位精度，期货为2位精度
    private Double costPrice;      //摊薄成本价（证券账户），平均开仓价（期货账户）。证券无精度限制，期货为2位精度，如果没传，代表此时此值无效
    private Double val;            //市值，3位精度, 期货此字段值为0
    private Double plVal;         //盈亏金额，3位精度，期货为2位精度
    private Double plRatio;       //盈亏百分比(如 plRatio 等于0.088代表涨8.8%)，无精度限制，如果没传，代表此时此值无效
    private Integer secMarket;      //证券所属市场，参见 TrdSecMarket 的枚举定义
    private String secMarketStr;
    //以下是此持仓今日统计
    private Double td_plVal;    //今日盈亏金额，3位精度，下同, 期货为2位精度
    private Double td_trdVal;    //今日交易额，期货不适用
    private Double td_buyVal;    //今日买入总额，期货不适用
    private Double td_buyQty;    //今日买入总量，期货不适用
    private Double td_sellVal;    //今日卖出总额，期货不适用
    private Double td_sellQty;    //今日卖出总量，期货不适用

    private Double unrealizedPL;       //未实现盈亏（仅期货账户适用）
    private Double realizedPL;         //已实现盈亏（仅期货账户适用）
    private Integer currency;        // 货币类型，取值参考 Currency
    private String currencyStr;
    private Integer trdMarket;  //交易市场, 参见 TrdMarket 的枚举定义
    private String trdMarketStr;

    public String getPositionSideStr() {
        return positionSideStr;
    }

    public void setPositionSideStr(String positionSideStr) {
        this.positionSideStr = positionSideStr;
    }

    public String getSecMarketStr() {
        return secMarketStr;
    }

    public void setSecMarketStr(String secMarketStr) {
        this.secMarketStr = secMarketStr;
    }

    public String getCurrencyStr() {
        return currencyStr;
    }

    public void setCurrencyStr(String currencyStr) {
        this.currencyStr = currencyStr;
    }

    public String getTrdMarketStr() {
        return trdMarketStr;
    }

    public void setTrdMarketStr(String trdMarketStr) {
        this.trdMarketStr = trdMarketStr;
    }

    public String getPositionID() {
        return positionID;
    }

    public void setPositionID(String positionID) {
        this.positionID = positionID;
    }

    public Integer getPositionSide() {
        return positionSide;
    }

    public void setPositionSide(Integer positionSide) {
        this.positionSide = positionSide;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getCanSellQty() {
        return canSellQty;
    }

    public void setCanSellQty(Double canSellQty) {
        this.canSellQty = canSellQty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
    }

    public Double getPlVal() {
        return plVal;
    }

    public void setPlVal(Double plVal) {
        this.plVal = plVal;
    }

    public Double getPlRatio() {
        return plRatio;
    }

    public void setPlRatio(Double plRatio) {
        this.plRatio = plRatio;
    }

    public Integer getSecMarket() {
        return secMarket;
    }

    public void setSecMarket(Integer secMarket) {
        this.secMarket = secMarket;
    }

    public Double getTd_plVal() {
        return td_plVal;
    }

    public void setTd_plVal(Double td_plVal) {
        this.td_plVal = td_plVal;
    }

    public Double getTd_trdVal() {
        return td_trdVal;
    }

    public void setTd_trdVal(Double td_trdVal) {
        this.td_trdVal = td_trdVal;
    }

    public Double getTd_buyVal() {
        return td_buyVal;
    }

    public void setTd_buyVal(Double td_buyVal) {
        this.td_buyVal = td_buyVal;
    }

    public Double getTd_buyQty() {
        return td_buyQty;
    }

    public void setTd_buyQty(Double td_buyQty) {
        this.td_buyQty = td_buyQty;
    }

    public Double getTd_sellVal() {
        return td_sellVal;
    }

    public void setTd_sellVal(Double td_sellVal) {
        this.td_sellVal = td_sellVal;
    }

    public Double getTd_sellQty() {
        return td_sellQty;
    }

    public void setTd_sellQty(Double td_sellQty) {
        this.td_sellQty = td_sellQty;
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

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getTrdMarket() {
        return trdMarket;
    }

    public void setTrdMarket(Integer trdMarket) {
        this.trdMarket = trdMarket;
    }
}
