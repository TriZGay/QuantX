package io.futakotome.trade.controller.vo;

import io.futakotome.trade.domain.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderRequest {
    @NotNull(message = "accId必填")
    @NotEmpty(message = "accId不能为空")
    private String accId;

    @NotNull(message = "tradeEnv(交易环境必填)")
    @EnumValid(target = TradeEnv.class, message = "输入错误,交易环境:[1(真实)/0(模拟)]")
    private Integer tradeEnv;

    @NotNull(message = "tradeMarket(交易市场必填)")
    @EnumValid(target = TradeMarket.class, message = "输入错误,交易市场:[1(香港)/2(美国)/3(A股模拟)/4(A股)/5(期货)]")
    private Integer tradeMarket;

    @NotNull(message = "tradeSide必填")
    @EnumValid(target = TradeSide.class, message = "输入错误,交易方向:[1(买入)/2(卖出)/3(卖空)/4(买回)]")
    private Integer tradeSide;

    @NotNull(message = "orderType必填")
    @EnumValid(target = OrderType.class,
            message = "输入错误,订单类型:[1(普通订单)/2(市价订单)/5(绝对限价订单)/6(竞价订单)/7(竞价限价订单)/8(特别限价订单)/9(特别限价且要求全部成交订单)/10(止损市价单)/11(止损限价单)/12(触及市价单)/13(触及限价单)/14(跟踪止损市价单)/15(跟踪止损限价单)]")
    private Integer orderType;

    @EnumValid(target = TradeSecurityMarket.class,
            message = "输入错误,证券所属市场:[1(香港市场)/2(美国市场)/31(沪股市场)/32(深股市场)/41(新加坡市场)/51(日本市场)]")
    private Integer securityMarket;

    @NotNull(message = "code必填")
    @NotEmpty(message = "code不能为空")
    private String code;

    @NotNull(message = "qty必填")
//    @NegativeOrZero
    private Double qty;

//    @Null
//    @NegativeOrZero
    private Double price;

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

    public Integer getTradeMarket() {
        return tradeMarket;
    }

    public void setTradeMarket(Integer tradeMarket) {
        this.tradeMarket = tradeMarket;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Integer getSecurityMarket() {
        return securityMarket;
    }

    public void setSecurityMarket(Integer securityMarket) {
        this.securityMarket = securityMarket;
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
}
