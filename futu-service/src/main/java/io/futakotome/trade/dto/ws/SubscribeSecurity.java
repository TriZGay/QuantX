package io.futakotome.trade.dto.ws;

import java.util.Objects;

public  class SubscribeSecurity {
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

}