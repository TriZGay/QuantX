package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

/**
 * @TableName t_position
 */
@TableName(value = "t_position")
public class PositionDto implements Serializable {
    @TableId
    private Long id;

    private Integer tradeEnv;

    private String accId;

    private Integer accTradeMarket;

    private Long positionId;

    private Integer positionSide;

    private String code;

    private String name;

    private Double qty;

    private Double canSellQty;

    private Double price;

    private Double costPrice;

    private Double val;

    private Double plVal;

    private Double plRatio;

    private Integer securityMarket;

    private Double tdPlVal;

    private Double tdTrdVal;

    private Double tdBuyVal;

    private Double tdBuyQty;

    private Double tdSellVal;

    private Double tdSellQty;

    private Double unrealizedPl;

    private Double realizedPl;

    private Integer currency;

    private Integer tradeMarket;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Integer getTradeEnv() {
        return tradeEnv;
    }

    public void setTradeEnv(Integer tradeEnv) {
        this.tradeEnv = tradeEnv;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public Integer getAccTradeMarket() {
        return accTradeMarket;
    }

    public void setAccTradeMarket(Integer accTradeMarket) {
        this.accTradeMarket = accTradeMarket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
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

    public Integer getSecurityMarket() {
        return securityMarket;
    }

    public void setSecurityMarket(Integer securityMarket) {
        this.securityMarket = securityMarket;
    }

    public Double getTdPlVal() {
        return tdPlVal;
    }

    public void setTdPlVal(Double tdPlVal) {
        this.tdPlVal = tdPlVal;
    }

    public Double getTdTrdVal() {
        return tdTrdVal;
    }

    public void setTdTrdVal(Double tdTrdVal) {
        this.tdTrdVal = tdTrdVal;
    }

    public Double getTdBuyVal() {
        return tdBuyVal;
    }

    public void setTdBuyVal(Double tdBuyVal) {
        this.tdBuyVal = tdBuyVal;
    }

    public Double getTdBuyQty() {
        return tdBuyQty;
    }

    public void setTdBuyQty(Double tdBuyQty) {
        this.tdBuyQty = tdBuyQty;
    }

    public Double getTdSellVal() {
        return tdSellVal;
    }

    public void setTdSellVal(Double tdSellVal) {
        this.tdSellVal = tdSellVal;
    }

    public Double getTdSellQty() {
        return tdSellQty;
    }

    public void setTdSellQty(Double tdSellQty) {
        this.tdSellQty = tdSellQty;
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
        PositionDto that = (PositionDto) o;
        return positionId.equals(that.positionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionId);
    }
}