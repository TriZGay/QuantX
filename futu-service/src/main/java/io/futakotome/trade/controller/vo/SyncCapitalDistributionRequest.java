package io.futakotome.trade.controller.vo;

import javax.validation.constraints.NotNull;

public class SyncCapitalDistributionRequest {
    @NotNull(message = "市场不能为空")
    private Integer market;
    @NotNull(message = "标的代码不能为空")
    private String code;

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
