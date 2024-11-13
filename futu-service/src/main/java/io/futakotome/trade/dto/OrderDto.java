package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @TableName t_order
 */
@TableName(value = "t_order")
public class OrderDto implements Serializable {
    @TableId
    private Long id;

    private String accId;

    private Integer tradeEnv;

    private Integer accTradeMarket;

    private Integer tradeSide;

    private Integer orderType;

    private Integer orderStatus;

    private String orderId;

    private String orderIdEx;

    private String code;

    private String name;

    private Double qty;

    private Double price;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Double fillQty;

    private Double fillAvgPrice;

    private String lastErrMsg;

    private Integer securityMarket;

    private String remark;

    private Double auxPrice;

    private Integer trailType;

    private Double trailValue;

    private Double trailSpread;

    private Integer currency;

    private Integer tradeMarket;

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

    public Integer getTradeEnv() {
        return tradeEnv;
    }

    public void setTradeEnv(Integer tradeEnv) {
        this.tradeEnv = tradeEnv;
    }

    public Integer getAccTradeMarket() {
        return accTradeMarket;
    }

    public void setAccTradeMarket(Integer accTradeMarket) {
        this.accTradeMarket = accTradeMarket;
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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderIdEx() {
        return orderIdEx;
    }

    public void setOrderIdEx(String orderIdEx) {
        this.orderIdEx = orderIdEx;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
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

    public Integer getSecurityMarket() {
        return securityMarket;
    }

    public void setSecurityMarket(Integer securityMarket) {
        this.securityMarket = securityMarket;
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

    public Integer getTradeMarket() {
        return tradeMarket;
    }

    public void setTradeMarket(Integer tradeMarket) {
        this.tradeMarket = tradeMarket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return orderId.equals(orderDto.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}