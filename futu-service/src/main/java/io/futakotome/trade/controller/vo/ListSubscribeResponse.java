package io.futakotome.trade.controller.vo;

public class ListSubscribeResponse {
    private Long id;

    private Integer securityMarket;

    private String securityCode;

    private String securityName;

    private Integer securityType;

    private Integer subType;

    private String subTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSecurityMarket() {
        return securityMarket;
    }

    public void setSecurityMarket(Integer securityMarket) {
        this.securityMarket = securityMarket;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSecurityName() {
        return securityName;
    }

    public String getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(String subTypes) {
        this.subTypes = subTypes;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public Integer getSecurityType() {
        return securityType;
    }

    public void setSecurityType(Integer securityType) {
        this.securityType = securityType;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }
}
