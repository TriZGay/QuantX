package io.futakotome.trade.controller.vo;

import java.util.Objects;

public class SubscribeSecurity {
    private Integer market;
    private String code;
    private String name;
    private Integer type;

    public SubscribeSecurity() {
    }

    public SubscribeSecurity(Integer market, String code) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscribeSecurity that = (SubscribeSecurity) o;
        return market.equals(that.market) && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(market, code);
    }
}
