package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @TableName t_snapshot_future_ex
 */
@TableName(value ="t_snapshot_future_ex")
public class SnapshotFutureExDto implements Serializable {
    private Long id;

    private Integer market;

    private String code;

    private Double lastSettlePrice;

    private Integer position;

    private Integer positionChange;

    private LocalDate lastTradeTime;

    private Boolean isMainContract;

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

    public Double getLastSettlePrice() {
        return lastSettlePrice;
    }

    public void setLastSettlePrice(Double lastSettlePrice) {
        this.lastSettlePrice = lastSettlePrice;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPositionChange() {
        return positionChange;
    }

    public void setPositionChange(Integer positionChange) {
        this.positionChange = positionChange;
    }

    public LocalDate getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(LocalDate lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public Boolean getIsMainContract() {
        return isMainContract;
    }

    public void setIsMainContract(Boolean isMainContract) {
        this.isMainContract = isMainContract;
    }
}