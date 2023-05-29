package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

/**
 * @TableName t_acc
 */
@TableName(value = "t_acc")
public class AccDto implements Serializable {
    @TableId
    private Long id;

    private Integer tradeEnv;

    private String accId;

    private String tradeMarketAuthList;

    private Integer accType;

    private String cardNum;

    private Integer firm;

    private Integer simAccType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getTradeMarketAuthList() {
        return tradeMarketAuthList;
    }

    public void setTradeMarketAuthList(String tradeMarketAuthList) {
        this.tradeMarketAuthList = tradeMarketAuthList;
    }

    public Integer getAccType() {
        return accType;
    }

    public void setAccType(Integer accType) {
        this.accType = accType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getFirm() {
        return firm;
    }

    public void setFirm(Integer firm) {
        this.firm = firm;
    }

    public Integer getSimAccType() {
        return simAccType;
    }

    public void setSimAccType(Integer simAccType) {
        this.simAccType = simAccType;
    }

    @Override
    public String toString() {
        return "AccDto{" +
                "id=" + id +
                ", tradeEnv=" + tradeEnv +
                ", accId='" + accId + '\'' +
                ", tradeMarketAuthList='" + tradeMarketAuthList + '\'' +
                ", accType=" + accType +
                ", cardNum='" + cardNum + '\'' +
                ", firm=" + firm +
                ", simAccType=" + simAccType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccDto accDto = (AccDto) o;
        return accId.equals(accDto.accId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accId);
    }
}