package io.trizgay.quantx.http.pojo;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(publicConverter = false, generateConverter = true)
public class PostSecurityListRequest {
    private Integer market;
    private String code;

    public PostSecurityListRequest(Integer market, String code) {
        this.market = market;
        this.code = code;
    }

    public PostSecurityListRequest(JsonObject jsonObject) {
        PostSecurityListRequestConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        PostSecurityListRequestConverter.toJson(this, jsonObject);
        return jsonObject;
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

    @Override
    public String toString() {
        return "PostSecurityListRequest{" +
                "market=" + market +
                ", code='" + code + '\'' +
                '}';
    }
}
