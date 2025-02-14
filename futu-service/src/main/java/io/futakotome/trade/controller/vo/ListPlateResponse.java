package io.futakotome.trade.controller.vo;

public class ListPlateResponse {
    private Long id;
    private String name;
    private String code;
    private String market;
    private Integer marketCode;
    private String plateType;
    private Integer plateTypeCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public Integer getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(Integer marketCode) {
        this.marketCode = marketCode;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public Integer getPlateTypeCode() {
        return plateTypeCode;
    }

    public void setPlateTypeCode(Integer plateTypeCode) {
        this.plateTypeCode = plateTypeCode;
    }
}
