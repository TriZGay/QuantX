package io.futakotome.trade.dto.ws;

public class PlaceOrderWsMessage implements Message {
    private String accId;
    private Integer tradeEnv;
    private Integer tradeMarket;
    private Integer tradeSide;
    private Integer orderType;
    private String code;
    private Double qty;
    private Double price; //价格，（证券账户精确到小数点后 3 位，期货账户精确到小数点后 9 位，超出部分会被舍弃）
    //以下2个为调整价格使用，都传才有效，对港、A 股有意义，因为港股有价位，A 股2位精度，美股可不传
    private Boolean adjustPrice; //是否调整价格，如果价格不合法，是否调整到合法价位，true 调整，false 不调整。如果价格不合法又不允许调整，则会返回错误
    private Double adjustSideAndLimit; //调整方向和调整幅度百分比限制，正数代表向上调整，负数代表向下调整，具体值代表调整幅度限制，如：0.015代表向上调整且幅度不超过1.5%；-0.01代表向下调整且幅度不超过1%
    private Integer secMarket; //证券所属市场，参/见 TrdSecMarket 的枚举定义
    private String remark; //用户备注字符串，最多只能传64字节。可用于标识订单唯一信息等，下单填上，订单结构就会带上。
    private Integer timeInForce; //订单有效期限，参见 TrdCommon.TimeInForce 的枚举定义
    private Boolean fillOutsideRTH; //是否允许盘前盘后成交。仅适用于美股限价单。默认 false
    private Double auxPrice; //触发价格
    private Integer trailType; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    private Double trailValue; //跟踪金额/百分比
    private Double trailSpread; //指定价差

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(Boolean adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    public Double getAdjustSideAndLimit() {
        return adjustSideAndLimit;
    }

    public void setAdjustSideAndLimit(Double adjustSideAndLimit) {
        this.adjustSideAndLimit = adjustSideAndLimit;
    }

    public Integer getSecMarket() {
        return secMarket;
    }

    public void setSecMarket(Integer secMarket) {
        this.secMarket = secMarket;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(Integer timeInForce) {
        this.timeInForce = timeInForce;
    }

    public Boolean getFillOutsideRTH() {
        return fillOutsideRTH;
    }

    public void setFillOutsideRTH(Boolean fillOutsideRTH) {
        this.fillOutsideRTH = fillOutsideRTH;
    }

    public Double getAuxPrice() {
        return auxPrice;
    }

    public void setAuxPrice(Double auxPrice) {
        this.auxPrice = auxPrice;
    }

    public Integer getTrailType() {
        return trailType;
    }

    public void setTrailType(Integer trailType) {
        this.trailType = trailType;
    }

    public Double getTrailValue() {
        return trailValue;
    }

    public void setTrailValue(Double trailValue) {
        this.trailValue = trailValue;
    }

    public Double getTrailSpread() {
        return trailSpread;
    }

    public void setTrailSpread(Double trailSpread) {
        this.trailSpread = trailSpread;
    }

    public Integer getTradeSide() {
        return tradeSide;
    }

    public void setTradeSide(Integer tradeSide) {
        this.tradeSide = tradeSide;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Integer getTradeMarket() {
        return tradeMarket;
    }

    public void setTradeMarket(Integer tradeMarket) {
        this.tradeMarket = tradeMarket;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public Integer getTradeEnv() {
        return tradeEnv;
    }

    public void setTradeEnv(Integer tradeEnv) {
        this.tradeEnv = tradeEnv;
    }

    @Override
    public MessageType getType() {
        return MessageType.PLACE_ORDER;
    }
}
