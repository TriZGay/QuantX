package io.futakotome.analyze.controller.vo;

import javax.validation.constraints.NotNull;

public class MetaRequest {
    @NotNull(message = "时间粒度必填")
    @EnumValid(target = Granularity.class, message = "粒度输入错误")
    private Integer granularity;

    public Integer getGranularity() {
        return granularity;
    }

    public void setGranularity(Integer granularity) {
        this.granularity = granularity;
    }
}
