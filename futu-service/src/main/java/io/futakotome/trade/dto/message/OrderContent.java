package io.futakotome.trade.dto.message;

public class OrderContent {
    private Integer trdSide; //交易方向, 参见 TrdSide 的枚举定义
    private Integer orderType; //订单类型, 参见 OrderType 的枚举定义
    private Integer orderStatus; //订单状态, 参见 OrderStatus 的枚举定义
    private String orderID; //订单号
    private String orderIDEx; //扩展订单号(仅查问题时备用)
    private String code; //代码
    private String name; //名称
    private Double qty; //订单数量，2位精度，期权单位是"张"
    private Double price; //订单价格，3位精度
    private String createTime; //创建时间，严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传
    private String updateTime; //最后更新时间，严格按 YYYY-MM-DD HH:MM:SS 或 YYYY-MM-DD HH:MM:SS.MS 格式传
    private Double fillQty; //成交数量，2位精度，期权单位是"张"
    private Double fillAvgPrice; //成交均价，无精度限制
    private String lastErrMsg; //最后的错误描述，如果有错误，会有此描述最后一次错误的原因，无错误为空
    private Integer secMarket; //证券所属市场，参见 TrdSecMarket 的枚举定义
    private Double createTimestamp; //创建时间戳
    private Double updateTimestamp; //最后更新时间戳
    private String remark; //用户备注字符串，最大长度64字节
    private Double auxPrice; //触发价格
    private Integer trailType; //跟踪类型, 参见Trd_Common.TrailType的枚举定义
    private Double trailValue; //跟踪金额/百分比
    private Double trailSpread; //指定价差
    private Integer currency;        // 货币类型，取值参考 Currency
    private Integer trdMarket;  //交易市场, 参见TrdMarket的枚举定义

    public Integer getTrdSide() {
        return trdSide;
    }

    public void setTrdSide(Integer trdSide) {
        this.trdSide = trdSide;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderIDEx() {
        return orderIDEx;
    }

    public void setOrderIDEx(String orderIDEx) {
        this.orderIDEx = orderIDEx;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Double getFillQty() {
        return fillQty;
    }

    public void setFillQty(Double fillQty) {
        this.fillQty = fillQty;
    }

    public Double getFillAvgPrice() {
        return fillAvgPrice;
    }

    public void setFillAvgPrice(Double fillAvgPrice) {
        this.fillAvgPrice = fillAvgPrice;
    }

    public String getLastErrMsg() {
        return lastErrMsg;
    }

    public void setLastErrMsg(String lastErrMsg) {
        this.lastErrMsg = lastErrMsg;
    }

    public Integer getSecMarket() {
        return secMarket;
    }

    public void setSecMarket(Integer secMarket) {
        this.secMarket = secMarket;
    }

    public Double getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Double createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public Double getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Double updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
