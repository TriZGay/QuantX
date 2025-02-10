package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

/**
 * @TableName t_sub
 */
@TableName(value = "t_sub")
public class SubDto implements Serializable {
    @TableId
    private Long id;

    private Integer securityMarket;

    private String securityCode;

    private String securityName;

    private Integer securityType;

    private Integer subType;

    @TableField(exist = false)
    private String subTypes;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSecurityMarket() {
        return securityMarket;
    }

    public void setSecurityMarket(Integer securityMarket) {
        this.securityMarket = securityMarket;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public String getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(String subTypes) {
        this.subTypes = subTypes;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public Integer getSecurityType() {
        return securityType;
    }

    public void setSecurityType(Integer securityType) {
        this.securityType = securityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubDto subDto = (SubDto) o;
        return securityMarket.equals(subDto.securityMarket) && securityCode.equals(subDto.securityCode) && subType.equals(subDto.subType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(securityMarket, securityCode, subType);
    }

    @Override
    public String toString() {
        return "SubDto{" +
                "id=" + id +
                ", securityMarket=" + securityMarket +
                ", securityCode='" + securityCode + '\'' +
                ", securityName='" + securityName + '\'' +
                ", securityType=" + securityType +
                ", subType=" + subType +
                '}';
    }
}