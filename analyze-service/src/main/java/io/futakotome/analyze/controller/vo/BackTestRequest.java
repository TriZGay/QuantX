package io.futakotome.analyze.controller.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BackTestRequest extends RangeRequest {
    @NotNull(message = "code必填")
    @NotEmpty(message = "code不能为空")
    private String code;

    @NotNull(message = "复权类型必填")
    private Integer rehabType;

    @NotNull(message = "时间粒度必填")
    @EnumValid(target = Granularity.class, message = "粒度输入错误")
    private Integer granularity;

    private Double initialCapital;

    private Double commission;

    public Double getInitialCapital() {
        return initialCapital;
    }

    public void setInitialCapital(Double initialCapital) {
        this.initialCapital = initialCapital;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Integer getGranularity() {
        return granularity;
    }

    public void setGranularity(Integer granularity) {
        this.granularity = granularity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getRehabType() {
        return rehabType;
    }

    public void setRehabType(Integer rehabType) {
        this.rehabType = rehabType;
    }
}
