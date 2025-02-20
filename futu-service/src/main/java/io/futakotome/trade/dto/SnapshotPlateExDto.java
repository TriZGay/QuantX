package io.futakotome.trade.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * @TableName t_snapshot_plate_ex
 */
@TableName(value ="t_snapshot_plate_ex")
public class SnapshotPlateExDto implements Serializable {
    private Long id;

    private Integer market;

    private String code;

    private Integer raiseCount;

    private Integer fallCount;

    private Integer equalCount;

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

    public Integer getRaiseCount() {
        return raiseCount;
    }

    public void setRaiseCount(Integer raiseCount) {
        this.raiseCount = raiseCount;
    }

    public Integer getFallCount() {
        return fallCount;
    }

    public void setFallCount(Integer fallCount) {
        this.fallCount = fallCount;
    }

    public Integer getEqualCount() {
        return equalCount;
    }

    public void setEqualCount(Integer equalCount) {
        this.equalCount = equalCount;
    }
}