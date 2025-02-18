package io.futakotome.trade.dto.message;

public class CommonSecurity {
    private Integer market;
    private String code;

    public CommonSecurity() {
    }

    public CommonSecurity(Integer market, String code) {
        this.market = market;
        this.code = code;
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
