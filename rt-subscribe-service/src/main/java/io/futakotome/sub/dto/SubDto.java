package io.futakotome.sub.dto;

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

    private String subType;

    private Integer usedQuota;

    private Integer isOwnConn;

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

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Integer getUsedQuota() {
        return usedQuota;
    }

    public void setUsedQuota(Integer usedQuota) {
        this.usedQuota = usedQuota;
    }

    public Integer getIsOwnConn() {
        return isOwnConn;
    }

    public void setIsOwnConn(Integer isOwnConn) {
        this.isOwnConn = isOwnConn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubDto subDto = (SubDto) o;
        return securityMarket.equals(subDto.securityMarket) && securityCode.equals(subDto.securityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(securityMarket, securityCode);
    }

    @Override
    public String toString() {
        return "SubDto{" +
                "id=" + id +
                ", securityMarket=" + securityMarket +
                ", securityCode='" + securityCode + '\'' +
                ", subType='" + subType + '\'' +
                ", usedQuota=" + usedQuota +
                ", isOwnConn=" + isOwnConn +
                '}';
    }
}