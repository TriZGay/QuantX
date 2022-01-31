package io.trizgay.quantx.http.pojo;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true, publicConverter = false)
public class PostPlateSetRequest {
    private Integer market;
    private Integer plateSetType;

    public PostPlateSetRequest(JsonObject jsonObject) {
        PostPlateSetRequestConverter.fromJson(jsonObject, this);
    }

    public PostPlateSetRequest(Integer market, Integer plateSetType) {
        this.market = market;
        this.plateSetType = plateSetType;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        PostPlateSetRequestConverter.toJson(this, jsonObject);
        return jsonObject;
    }

    public Integer getMarket() {
        return market;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public Integer getPlateSetType() {
        return plateSetType;
    }

    public void setPlateSetType(Integer plateSetType) {
        this.plateSetType = plateSetType;
    }

    @Override
    public String toString() {
        return "PostPlateSetRequest{" +
                "market=" + market +
                ", plateSetType=" + plateSetType +
                '}';
    }
}
