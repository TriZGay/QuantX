package io.futakotome.itick.controller.vo;

public class SymbolListRequest {
    //stock、forex、indices、crypto
    private String type;
    //（HK、SZ、SH、US、SG），外汇（gb），指数（gb），数字币（ba）
    private String region;
    private String code;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
