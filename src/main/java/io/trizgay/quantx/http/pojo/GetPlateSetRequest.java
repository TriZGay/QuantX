package io.trizgay.quantx.http.pojo;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true, publicConverter = false)
public class GetPlateSetRequest {
    private Integer market;
    private Integer plateSetType;

    public GetPlateSetRequest(JsonObject jsonObject) {
        GetPlateSetRequestConverter.fromJson(jsonObject, this);
    }

    public GetPlateSetRequest(Integer market, Integer plateSetType) {
        this.market = market;
        this.plateSetType = plateSetType;
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        GetPlateSetRequestConverter.toJson(this, jsonObject);
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
        return "GetPlateSetRequest{" +
                "market=" + market +
                ", plateSetType=" + plateSetType +
                '}';
    }
}
