package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName t_capital_distribution
 */
@TableName(value = "t_capital_distribution")
public class CapitalDistributionDto implements Serializable {
    private Long id;

    private Integer market;

    private String code;

    private Double capitalInSuper;

    private Double capitalInBig;

    private Double capitalInMid;

    private Double capitalInSmall;

    private Double capitalOutSuper;

    private Double capitalOutBig;

    private Double capitalOutMid;

    private Double capitalOutSmall;

    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getCapitalInSuper() {
        return capitalInSuper;
    }

    public void setCapitalInSuper(Double capitalInSuper) {
        this.capitalInSuper = capitalInSuper;
    }

    public Double getCapitalInBig() {
        return capitalInBig;
    }

    public void setCapitalInBig(Double capitalInBig) {
        this.capitalInBig = capitalInBig;
    }

    public Double getCapitalInMid() {
        return capitalInMid;
    }

    public void setCapitalInMid(Double capitalInMid) {
        this.capitalInMid = capitalInMid;
    }

    public Double getCapitalInSmall() {
        return capitalInSmall;
    }

    public void setCapitalInSmall(Double capitalInSmall) {
        this.capitalInSmall = capitalInSmall;
    }

    public Double getCapitalOutSuper() {
        return capitalOutSuper;
    }

    public void setCapitalOutSuper(Double capitalOutSuper) {
        this.capitalOutSuper = capitalOutSuper;
    }

    public Double getCapitalOutBig() {
        return capitalOutBig;
    }

    public void setCapitalOutBig(Double capitalOutBig) {
        this.capitalOutBig = capitalOutBig;
    }

    public Double getCapitalOutMid() {
        return capitalOutMid;
    }

    public void setCapitalOutMid(Double capitalOutMid) {
        this.capitalOutMid = capitalOutMid;
    }

    public Double getCapitalOutSmall() {
        return capitalOutSmall;
    }

    public void setCapitalOutSmall(Double capitalOutSmall) {
        this.capitalOutSmall = capitalOutSmall;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}