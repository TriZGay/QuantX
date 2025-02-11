package io.futakotome.trade.controller.vo;

import io.futakotome.trade.domain.code.PeriodType;

import javax.validation.constraints.NotNull;

public class SyncCapitalFlowRequest {
    @NotNull(message = "周期类型必填")
    @EnumValid(target = PeriodType.class, message = "输入错误,周期类型:[0(实时)/1(日)/2(周)/3(月)]")
    private Integer periodType;
    @NotNull(message = "市场不能为空")
    private Integer market;
    @NotNull(message = "标的代码不能为空")
    private String code;

    private String beginDate;
    private String endDate;

    public Integer getPeriodType() {
        return periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
