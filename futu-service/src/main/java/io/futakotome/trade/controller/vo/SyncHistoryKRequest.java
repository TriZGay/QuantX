package io.futakotome.trade.controller.vo;

import io.futakotome.trade.domain.KLType;

import javax.validation.constraints.NotNull;

public class SyncHistoryKRequest {
    @NotNull(message = "市场不能为空")
    private Integer market;
    @NotNull(message = "标的代码不能为空")
    private String code;
    @NotNull(message = "K线类型必填")
    @EnumValid(target = KLType.class, message = "请填入符合码表的值")
    private Integer klType;
    @NotNull(message = "开始日期必填")
    private String beginDate;
    @NotNull(message = "结束日期必填")
    private String endDate;

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

    public Integer getKlType() {
        return klType;
    }

    public void setKlType(Integer klType) {
        this.klType = klType;
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
}
