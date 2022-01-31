package io.trizgay.quantx.http.pojo;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(publicConverter = false, generateConverter = true)
public class PostIpoInfoRequest {
    private Integer market;

    public PostIpoInfoRequest(Integer market) {
        this.market = market;
    }

    public PostIpoInfoRequest(JsonObject jsonObject) {
        PostIpoInfoRequestConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        PostIpoInfoRequestConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    @Override
    public String toString() {
        return "PostIpoInfoRequest{" +
                "market=" + market +
                '}';
    }
}
