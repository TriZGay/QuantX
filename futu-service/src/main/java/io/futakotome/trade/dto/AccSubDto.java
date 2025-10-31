package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

/**
 * @TableName t_acc_sub
 */
@TableName(value ="t_acc_sub")
public class AccSubDto implements Serializable {
    private Long id;

    private String accId;

    private String cardNum;

    private String uniCardNum;

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

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getUniCardNum() {
        return uniCardNum;
    }

    public void setUniCardNum(String uniCardNum) {
        this.uniCardNum = uniCardNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccSubDto accSubDto = (AccSubDto) o;
        return Objects.equals(accId, accSubDto.accId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accId);
    }
}